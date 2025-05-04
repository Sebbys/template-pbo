package com.kidaro.kael.seed;
import com.kidaro.kael.model.Driver;
import com.kidaro.kael.model.DriverStandings;
import com.kidaro.kael.model.Team;
import com.kidaro.kael.model.User;
import com.kidaro.kael.repository.DriverRepository;
import com.kidaro.kael.repository.DriverStandingRepository;
import com.kidaro.kael.repository.TeamRepository;
import com.kidaro.kael.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final DriverStandingRepository driverStandingRepo;
    private final DriverRepository driverRepo;
    private final TeamRepository teamRepo;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepo.count() == 0) {
            userRepo.save(new User(null, "admin", "admin123", "ADMIN"));
            userRepo.save(new User(null, "guest", "guest123", "GUEST"));
        }

        if (driverStandingRepo.count() == 0) {
            Team redBull = teamRepo.save(new Team(null, "Red Bull Racing"));
            Team mercedes = teamRepo.save(new Team(null, "Mercedes-AMG Petronas"));
            Team ferrari = teamRepo.save(new Team(null, "Scuderia Ferrari"));
            Team mclaren = teamRepo.save(new Team(null, "McLaren F1 Team"));

            Driver verstappen = driverRepo.save(new Driver(null, "Max Verstappen", redBull));
            Driver perez = driverRepo.save(new Driver(null, "Sergio Pérez", redBull));
            Driver hamilton = driverRepo.save(new Driver(null, "Lewis Hamilton", mercedes));
            Driver russell = driverRepo.save(new Driver(null, "George Russell", mercedes));
            Driver leclerc = driverRepo.save(new Driver(null, "Charles Leclerc", ferrari));
            Driver sainz = driverRepo.save(new Driver(null, "Carlos Sainz Jr.", ferrari));
            Driver norris = driverRepo.save(new Driver(null, "Lando Norris", mclaren));
            Driver piastri = driverRepo.save(new Driver(null, "Oscar Piastri", mclaren));

            driverStandingRepo.save(new DriverStandings(null, verstappen, 300));
            driverStandingRepo.save(new DriverStandings(null, hamilton, 200));
            driverStandingRepo.save(new DriverStandings(null, leclerc, 180));
            driverStandingRepo.save(new DriverStandings(null, norris, 150));
            driverStandingRepo.save(new DriverStandings(null, perez, 145));
            driverStandingRepo.save(new DriverStandings(null, russell, 130));
            driverStandingRepo.save(new DriverStandings(null, sainz, 125));
            driverStandingRepo.save(new DriverStandings(null, piastri, 110));
        }
    }
}
