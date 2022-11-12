package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class CarRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository cr = new CrudRepository(sf);

    @BeforeAll
    public static void getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    }

    @AfterEach
    public void cleanTables() {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.createMutationQuery("DELETE Car").executeUpdate();
            session.createMutationQuery("DELETE Engine").executeUpdate();
            session.createMutationQuery("DELETE Driver").executeUpdate();
            session.createMutationQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exc) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw exc;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Test
    public void forcedClean() {
        cleanTables();
    }

    @Test
    public void whenCreateThenFindByIdReturnsCarWithNotNullId() {
        CarRepository carRepository = new CarRepository(cr);
        EngineRepository engineRepository = new EngineRepository(cr);
        DriverRepository driverRepository = new DriverRepository(cr);
        UserRepository userRepository = new UserRepository(cr);
        Engine engine = new Engine();
        engine.setName("JET 900");
        engineRepository.create(engine);
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        userRepository.create(user2);
        Driver driver1 = new Driver();
        driver1.setName("Valera");
        driver1.setUser(user1);
        Driver driver2 = new Driver();
        driver2.setName("Ivan");
        driver2.setUser(user2);
        driverRepository.create(driver1);
        Car car = new Car();
        car.setName("Toyota prius");
        car.setEngine(engine);
        car.setDriver(driver1);
        car.setOwners(Set.of(driver2));
        carRepository.create(car);
        Car carFromDB = carRepository.findById(car.getId()).get();
        assertThat(carFromDB.getId()).isNotEqualTo(0);
        assertThat(carFromDB.getEngine().getId()).isEqualTo(engine.getId());
        assertThat(carFromDB.getEngine().getName()).isEqualTo(engine.getName());
        assertThat(carFromDB.getName()).isEqualTo(car.getName());
        assertThat(carFromDB.getDriver().getId()).isEqualTo(driver1.getId());
        assertThat(carFromDB.getDriver().getName()).isEqualTo(driver1.getName());
        assertThat(carFromDB.getDriver().getUser().getId()).isEqualTo(user1.getId());
        assertThat(carFromDB.getDriver().getUser().getLogin())
                .isEqualTo(user1.getLogin());
        assertThat(carFromDB.getDriver().getUser().getPassword())
                .isEqualTo(user1.getPassword());
        assertThat(carFromDB.getOwners().contains(driver2)).isTrue();
    }

    @Test
    public void whenUpdateThenMustBeChangedCar() {
        CarRepository carRepository = new CarRepository(cr);
        EngineRepository engineRepository = new EngineRepository(cr);
        DriverRepository driverRepository = new DriverRepository(cr);
        UserRepository userRepository = new UserRepository(cr);
        Engine engine1 = new Engine();
        engine1.setName("JET 900");
        Engine engine2 = new Engine();
        engine2.setName("IFK 1070");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        userRepository.create(user2);
        Driver driver1 = new Driver();
        driver1.setName("Valera");
        driver1.setUser(user1);
        driverRepository.create(driver1);
        Driver driver2 = new Driver();
        driver2.setName("Ivan");
        driver2.setUser(user2);
        driverRepository.create(driver2);
        Car car = new Car();
        car.setName("Toyota");
        car.setEngine(engine1);
        car.setDriver(driver1);
        carRepository.create(car);
        Car changedCar = new Car();
        changedCar.setId(car.getId());
        changedCar.setName("ChangedCar");
        changedCar.setEngine(engine2);
        changedCar.setDriver(driver2);
        carRepository.update(changedCar);
        Car carFromDB  = carRepository.findById(car.getId()).get();
        assertThat(carFromDB.getName()).isEqualTo(changedCar.getName());
        assertThat(carFromDB.getEngine()).isEqualTo(engine2);
        assertThat(carFromDB.getDriver().getName()).isEqualTo(driver2.getName());
        assertThat(carFromDB.getDriver().getUser().getId()).isEqualTo(user2.getId());
        assertThat(carFromDB.getDriver().getUser().getLogin())
                .isEqualTo(user2.getLogin());
        assertThat(carFromDB.getDriver().getUser().getPassword())
                .isEqualTo(user2.getPassword());
    }

    @Test
    public void whenAddTwoCarsThenFindAllReturnsBoth() {
        CarRepository carRepository = new CarRepository(cr);
        EngineRepository engineRepository = new EngineRepository(cr);
        DriverRepository driverRepository = new DriverRepository(cr);
        UserRepository userRepository = new UserRepository(cr);
        Engine engine1 = new Engine();
        engine1.setName("JET 900");
        Engine engine2 = new Engine();
        engine2.setName("IFK 1070");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        userRepository.create(user2);
        Driver driver1 = new Driver();
        driver1.setName("Valera");
        driver1.setUser(user1);
        driverRepository.create(driver1);
        Driver driver2 = new Driver();
        driver2.setName("Ivan");
        driver2.setUser(user2);
        driverRepository.create(driver2);
        Car car1 = new Car();
        car1.setName("Toyota");
        car1.setEngine(engine1);
        car1.setDriver(driver1);
        carRepository.create(car1);
        Car car2 = new Car();
        car2.setName("Renault");
        car2.setEngine(engine2);
        car2.setDriver(driver2);
        carRepository.create(car2);
        List<Car> carsFromDB = carRepository.findAll();
        assertThat(carsFromDB.get(0).getId()).isEqualTo(car1.getId());
        assertThat(carsFromDB.get(0).getName()).isEqualTo(car1.getName());
        assertThat(carsFromDB.get(0).getEngine()).isEqualTo(engine1);
        assertThat(carsFromDB.get(0).getDriver().getId()).isEqualTo(driver1.getId());
        assertThat(carsFromDB.get(0).getDriver().getName())
                .isEqualTo(driver1.getName());
        assertThat(carsFromDB.get(0).getDriver().getUser().getId())
                .isEqualTo(user1.getId());
        assertThat(carsFromDB.get(0).getDriver().getUser().getLogin())
                .isEqualTo(user1.getLogin());
        assertThat(carsFromDB.get(0).getDriver().getUser().getPassword())
                .isEqualTo(user1.getPassword());
        assertThat(carsFromDB.get(1).getId()).isEqualTo(car2.getId());
        assertThat(carsFromDB.get(1).getName()).isEqualTo(car2.getName());
        assertThat(carsFromDB.get(1).getEngine()).isEqualTo(engine2);
        assertThat(carsFromDB.get(1).getDriver().getId()).isEqualTo(driver2.getId());
        assertThat(carsFromDB.get(1).getDriver().getName())
                .isEqualTo(driver2.getName());
        assertThat(carsFromDB.get(1).getDriver().getUser().getId())
                .isEqualTo(user2.getId());
        assertThat(carsFromDB.get(1).getDriver().getUser().getLogin())
                .isEqualTo(user2.getLogin());
        assertThat(carsFromDB.get(1).getDriver().getUser().getPassword())
                .isEqualTo(user2.getPassword());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        CarRepository carRepository = new CarRepository(cr);
        EngineRepository engineRepository = new EngineRepository(cr);
        DriverRepository driverRepository = new DriverRepository(cr);
        UserRepository userRepository = new UserRepository(cr);
        Engine engine = new Engine();
        engine.setName("JET 900");
        engineRepository.create(engine);
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        userRepository.create(user);
        Driver driver = new Driver();
        driver.setName("Valera");
        driver.setUser(user);
        driverRepository.create(driver);
        Car car = new Car();
        car.setName("Toyota");
        car.setEngine(engine);
        car.setDriver(driver);
        carRepository.create(car);
        carRepository.delete(car.getId());
        List<Car> carsFromDB = carRepository.findAll();
        assertThat(carsFromDB.isEmpty()).isTrue();
    }
}