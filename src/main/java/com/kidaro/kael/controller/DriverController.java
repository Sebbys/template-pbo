package com.kidaro.kael.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kidaro.kael.model.Driver;
import com.kidaro.kael.model.DriverStandings;
import com.kidaro.kael.model.Team;
import com.kidaro.kael.repository.DriverRepository;
import com.kidaro.kael.repository.DriverStandingRepository;
import com.kidaro.kael.repository.TeamRepository;
import com.kidaro.kael.model.User;
import com.kidaro.kael.service.AuthService;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverStandingRepository driverStandingRepo;
    private final DriverRepository driverRepo;
    private final TeamRepository teamRepo;
    private final AuthService authService;

    private Optional<User> getUserFromRequest(HttpServletRequest request) {
        String username = null, password = null;
        for (Cookie cookie : Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])) {
            if (cookie.getName().equals("username")) username = cookie.getValue();
            if (cookie.getName().equals("password")) password = cookie.getValue();
        }
        if (username != null && password != null) {
            return authService.authenticate(username, password);
        }
        return Optional.empty();
    }

    private Map<String, Object> mapStandingToResponse(DriverStandings standing) {
        Driver driver = standing.getDriver();
        String driverName = "Unknown Driver";
        String teamName = "Unknown Team";

        if (driver != null) {
            driverName = driver.getName();
            Team team = driver.getTeam();
            if (team != null) {
                teamName = team.getName();
            }
        }

        return Map.of(
                "id", standing.getId(),
                "driverName", driverName,
                "team", teamName,
                "points", standing.getPoints()
        );
    }

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return driverStandingRepo.findAll().stream()
                .map(this::mapStandingToResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> payload, HttpServletRequest request) {
        Optional<User> adminUser = getUserFromRequest(request)
                .filter(user -> user.getRole().equals("ADMIN"));

        if (adminUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

        try {
            String driverName = (String) payload.get("driverName");
            String teamName = (String) payload.get("team");
            Integer points = (Integer) payload.get("points");

            if (driverName == null || teamName == null || points == null) {
                return ResponseEntity.badRequest().body("Missing required fields: driverName, team, points");
            }

            Team team = teamRepo.findByName(teamName)
                    .orElseGet(() -> teamRepo.save(new Team(null, teamName)));

            Driver driver = driverRepo.findByName(driverName)
                    .orElseGet(() -> driverRepo.save(new Driver(null, driverName, team)));

            if (!driver.getTeam().getId().equals(team.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Driver '" + driverName + "' exists but belongs to a different team ('" + driver.getTeam().getName() + "').");
            }

            Optional<DriverStandings> existingStanding = driverStandingRepo.findByDriver(driver);
            if (existingStanding.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Standing for driver '" + driverName + "' already exists.");
            }

            DriverStandings newStanding = new DriverStandings(null, driver, points);
            DriverStandings savedStanding = driverStandingRepo.save(newStanding);

            return ResponseEntity.status(HttpStatus.CREATED).body(mapStandingToResponse(savedStanding));

        } catch (Exception e) {
            System.err.println("Error creating standing: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating standing: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> payload, HttpServletRequest request) {
        Optional<User> adminUser = getUserFromRequest(request)
                .filter(user -> user.getRole().equals("ADMIN"));

        if (adminUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

        try {
            DriverStandings standing = driverStandingRepo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Standing not found with id: " + id));

            boolean updated = false;

            if (payload.containsKey("points")) {
                Object pointsObj = payload.get("points");
                if (pointsObj instanceof Integer) {
                    standing.setPoints((Integer) pointsObj);
                    updated = true;
                } else {
                    return ResponseEntity.badRequest().body("Invalid points value type.");
                }
            }

            if (updated) {
                DriverStandings savedStanding = driverStandingRepo.save(standing);
                return ResponseEntity.ok(mapStandingToResponse(savedStanding));
            } else {
                return ResponseEntity.ok(mapStandingToResponse(standing));
            }

        } catch (ResponseStatusException rse) {
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        } catch (Exception e) {
            System.err.println("Error updating standing: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating standing: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request) {
        Optional<User> adminUser = getUserFromRequest(request)
                .filter(user -> user.getRole().equals("ADMIN"));

        if (adminUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

        if (!driverStandingRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Standing not found with id: " + id);
        }

        try {
            driverStandingRepo.deleteById(id);
            return ResponseEntity.ok("Deleted standing with id: " + id);
        } catch (Exception e) {
            System.err.println("Error deleting standing: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting standing: " + e.getMessage());
        }
    }
}
