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
import ru.job4j.cars.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CarRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository cr = new CrudRepository(sf);
    private final CarRepository carStore = new CarRepository(cr);
    private final EngineRepository engineStore = new EngineRepository(cr);
    private final DriverRepository driverStore = new DriverRepository(cr);
    private final UserRepository userStore = new UserRepository(cr);
    private final CarBrandRepository carBrandStore = new CarBrandRepository(cr);
    private final CarBodyRepository carBodyStore = new CarBodyRepository(cr);
    private final WheelDriveRepository wheelDriveStore = new WheelDriveRepository(cr);
    private final CarColourRepository carColourStore = new CarColourRepository(cr);
    private final TransmissionRepository transmissionStore = new TransmissionRepository(cr);
    private final YearRepository yearStore = new YearRepository(cr);

    @BeforeAll
    public static void getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    }

    @BeforeAll
    public static void cleanTablesBefore() {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.createMutationQuery("DELETE Car").executeUpdate();
            session.createMutationQuery("DELETE Engine").executeUpdate();
            session.createMutationQuery("DELETE Driver").executeUpdate();
            session.createMutationQuery("DELETE User").executeUpdate();
            session.createMutationQuery("DELETE CarBrand").executeUpdate();
            session.createMutationQuery("DELETE CarBody").executeUpdate();
            session.createMutationQuery("DELETE WheelDrive").executeUpdate();
            session.createMutationQuery("DELETE CarColour").executeUpdate();
            session.createMutationQuery("DELETE Transmission").executeUpdate();
            session.createMutationQuery("DELETE Year").executeUpdate();
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

    @AfterEach
    public void cleanTablesAfter() {
        cleanTablesBefore();
    }

    @Test
    public void whenCreateThenFindByIdReturnsCarWithNotNullId() {
        Engine engine = new Engine();
        engine.setName("1.5/Бензин");
        engineStore.create(engine);
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        userStore.create(user);
        Driver driver = new Driver();
        driver.setName("Valera");
        driver.setUser(user);
        driverStore.create(driver);
        CarBrand carBrand = new CarBrand();
        carBrand.setName("Toyota");
        carBrandStore.create(carBrand);
        CarBody carBody = new CarBody();
        carBody.setName("Универсал");
        carBodyStore.create(carBody);
        WheelDrive wheelDrive = new WheelDrive();
        wheelDrive.setName("Полный");
        wheelDriveStore.create(wheelDrive);
        CarColour carColour = new CarColour();
        carColour.setName("Черный");
        carColourStore.create(carColour);
        Transmission transmission = new Transmission();
        transmission.setName("Автомат");
        transmissionStore.create(transmission);
        Year year = new Year();
        year.setYear(2015);
        yearStore.create(year);
        Car car = new Car();
        car.setMileage(70000);
        car.setEngine(engine);
        car.setDriver(driver);
        car.setCarBrand(carBrand);
        car.setCarBody(carBody);
        car.setWheelDrive(wheelDrive);
        car.setCarColour(carColour);
        car.setTransmission(transmission);
        car.setYear(year);
        carStore.create(car);
        Car carFromDB = carStore.findById(car.getId()).get();
        assertThat(carFromDB.getMileage()).isEqualTo(car.getMileage());
        assertThat(carFromDB.getEngine().getId()).isEqualTo(car.getEngine().getId());
        assertThat(carFromDB.getEngine().getName()).isEqualTo(car.getEngine().getName());
        assertThat(carFromDB.getDriver().getId()).isEqualTo(car.getDriver().getId());
        assertThat(carFromDB.getDriver().getName()).isEqualTo(car.getDriver().getName());
        assertThat(carFromDB.getDriver().getUser().getId()).isEqualTo(car.getDriver()
                .getUser().getId());
        assertThat(carFromDB.getDriver().getUser().getLogin()).isEqualTo(car.getDriver()
                .getUser().getLogin());
        assertThat(carFromDB.getDriver().getUser().getPassword()).isEqualTo(car.getDriver()
                .getUser().getPassword());
        assertThat(carFromDB.getCarBrand().getId()).isEqualTo(car.getCarBrand().getId());
        assertThat(carFromDB.getCarBrand().getName()).isEqualTo(car.getCarBrand().getName());
        assertThat(carFromDB.getCarBody().getId()).isEqualTo(car.getCarBody().getId());
        assertThat(carFromDB.getCarBody().getName()).isEqualTo(car.getCarBody().getName());
        assertThat(carFromDB.getWheelDrive().getId()).isEqualTo(car.getWheelDrive().getId());
        assertThat(carFromDB.getWheelDrive().getName()).isEqualTo(car.getWheelDrive().getName());
        assertThat(carFromDB.getCarColour().getId()).isEqualTo(car.getCarColour().getId());
        assertThat(carFromDB.getCarColour().getName()).isEqualTo(car.getCarColour().getName());
        assertThat(carFromDB.getTransmission().getId()).isEqualTo(car.getTransmission().getId());
        assertThat(carFromDB.getTransmission().getName()).isEqualTo(car.getTransmission().getName());
        assertThat(carFromDB.getYear().getId()).isEqualTo(car.getYear().getId());
        assertThat(carFromDB.getYear().getYear()).isEqualTo(car.getYear().getYear());
    }

    @Test
    public void whenAddTwoCarsThenFindAllReturnsBoth() {
        Engine engine1 = new Engine();
        engine1.setName("1.5/Бензин");
        engineStore.create(engine1);
        User user1 = new User();
        user1.setLogin("login");
        user1.setPassword("password");
        userStore.create(user1);
        Driver driver1 = new Driver();
        driver1.setName("Valera");
        driver1.setUser(user1);
        driverStore.create(driver1);
        CarBrand carBrand1 = new CarBrand();
        carBrand1.setName("Toyota");
        carBrandStore.create(carBrand1);
        CarBody carBody1 = new CarBody();
        carBody1.setName("Универсал");
        carBodyStore.create(carBody1);
        WheelDrive wheelDrive1 = new WheelDrive();
        wheelDrive1.setName("Полный");
        wheelDriveStore.create(wheelDrive1);
        CarColour carColour1 = new CarColour();
        carColour1.setName("Черный");
        carColourStore.create(carColour1);
        Transmission transmission1 = new Transmission();
        transmission1.setName("Автомат");
        transmissionStore.create(transmission1);
        Year year1 = new Year();
        year1.setYear(2000);
        yearStore.create(year1);
        Car car1 = new Car();
        car1.setMileage(100000);
        car1.setEngine(engine1);
        car1.setDriver(driver1);
        car1.setCarBrand(carBrand1);
        car1.setCarBody(carBody1);
        car1.setWheelDrive(wheelDrive1);
        car1.setCarColour(carColour1);
        car1.setTransmission(transmission1);
        car1.setYear(year1);
        carStore.create(car1);
        Engine engine2 = new Engine();
        engine2.setName("2.0/Дизель");
        engineStore.create(engine2);
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        userStore.create(user2);
        Driver driver2 = new Driver();
        driver2.setName("Ivan");
        driver2.setUser(user2);
        driverStore.create(driver2);
        CarBrand carBrand2 = new CarBrand();
        carBrand2.setName("KIA");
        carBrandStore.create(carBrand2);
        CarBody carBody2 = new CarBody();
        carBody2.setName("Седан");
        carBodyStore.create(carBody2);
        WheelDrive wheelDrive2 = new WheelDrive();
        wheelDrive2.setName("Задний");
        wheelDriveStore.create(wheelDrive2);
        CarColour carColour2 = new CarColour();
        carColour2.setName("Белый");
        carColourStore.create(carColour2);
        Transmission transmission2 = new Transmission();
        transmission2.setName("Механика");
        transmissionStore.create(transmission2);
        Year year2 = new Year();
        year2.setYear(2015);
        yearStore.create(year2);
        Car car2 = new Car();
        car2.setMileage(70000);
        car2.setEngine(engine2);
        car2.setDriver(driver2);
        car2.setCarBrand(carBrand2);
        car2.setCarBody(carBody2);
        car2.setWheelDrive(wheelDrive2);
        car2.setCarColour(carColour2);
        car2.setTransmission(transmission2);
        car2.setYear(year2);
        carStore.create(car2);
        List<Car> carsFromDB = carStore.findAll();
        assertThat(carsFromDB.get(0).getMileage()).isEqualTo(car1.getMileage());
        assertThat(carsFromDB.get(0).getEngine().getId()).isEqualTo(car1.getEngine().getId());
        assertThat(carsFromDB.get(0).getEngine().getName()).isEqualTo(car1.getEngine().getName());
        assertThat(carsFromDB.get(0).getDriver().getId()).isEqualTo(car1.getDriver().getId());
        assertThat(carsFromDB.get(0).getDriver().getName()).isEqualTo(car1.getDriver().getName());
        assertThat(carsFromDB.get(0).getDriver().getUser().getId()).isEqualTo(car1.getDriver()
                .getUser().getId());
        assertThat(carsFromDB.get(0).getDriver().getUser().getLogin()).isEqualTo(car1.getDriver()
                .getUser().getLogin());
        assertThat(carsFromDB.get(0).getDriver().getUser().getPassword()).isEqualTo(car1.getDriver()
                .getUser().getPassword());
        assertThat(carsFromDB.get(0).getCarBrand().getId()).isEqualTo(car1.getCarBrand().getId());
        assertThat(carsFromDB.get(0).getCarBrand().getName()).isEqualTo(car1.getCarBrand().getName());
        assertThat(carsFromDB.get(0).getCarBody().getId()).isEqualTo(car1.getCarBody().getId());
        assertThat(carsFromDB.get(0).getCarBody().getName()).isEqualTo(car1.getCarBody().getName());
        assertThat(carsFromDB.get(0).getWheelDrive().getId()).isEqualTo(car1.getWheelDrive().getId());
        assertThat(carsFromDB.get(0).getWheelDrive().getName()).isEqualTo(car1.getWheelDrive().getName());
        assertThat(carsFromDB.get(0).getCarColour().getId()).isEqualTo(car1.getCarColour().getId());
        assertThat(carsFromDB.get(0).getCarColour().getName()).isEqualTo(car1.getCarColour().getName());
        assertThat(carsFromDB.get(0).getTransmission().getId()).isEqualTo(car1.getTransmission().getId());
        assertThat(carsFromDB.get(0).getTransmission().getName()).isEqualTo(car1.getTransmission().getName());
        assertThat(carsFromDB.get(0).getYear().getId()).isEqualTo(car1.getYear().getId());
        assertThat(carsFromDB.get(0).getYear().getYear()).isEqualTo(car1.getYear().getYear());
        assertThat(carsFromDB.get(1).getMileage()).isEqualTo(car2.getMileage());
        assertThat(carsFromDB.get(1).getEngine().getId()).isEqualTo(car2.getEngine().getId());
        assertThat(carsFromDB.get(1).getEngine().getName()).isEqualTo(car2.getEngine().getName());
        assertThat(carsFromDB.get(1).getDriver().getId()).isEqualTo(car2.getDriver().getId());
        assertThat(carsFromDB.get(1).getDriver().getName()).isEqualTo(car2.getDriver().getName());
        assertThat(carsFromDB.get(1).getDriver().getUser().getId()).isEqualTo(car2.getDriver()
                .getUser().getId());
        assertThat(carsFromDB.get(1).getDriver().getUser().getLogin()).isEqualTo(car2.getDriver()
                .getUser().getLogin());
        assertThat(carsFromDB.get(1).getDriver().getUser().getPassword()).isEqualTo(car2.getDriver()
                .getUser().getPassword());
        assertThat(carsFromDB.get(1).getCarBrand().getId()).isEqualTo(car2.getCarBrand().getId());
        assertThat(carsFromDB.get(1).getCarBrand().getName()).isEqualTo(car2.getCarBrand().getName());
        assertThat(carsFromDB.get(1).getCarBody().getId()).isEqualTo(car2.getCarBody().getId());
        assertThat(carsFromDB.get(1).getCarBody().getName()).isEqualTo(car2.getCarBody().getName());
        assertThat(carsFromDB.get(1).getWheelDrive().getId()).isEqualTo(car2.getWheelDrive().getId());
        assertThat(carsFromDB.get(1).getWheelDrive().getName()).isEqualTo(car2.getWheelDrive().getName());
        assertThat(carsFromDB.get(1).getCarColour().getId()).isEqualTo(car2.getCarColour().getId());
        assertThat(carsFromDB.get(1).getCarColour().getName()).isEqualTo(car2.getCarColour().getName());
        assertThat(carsFromDB.get(1).getTransmission().getId()).isEqualTo(car2.getTransmission().getId());
        assertThat(carsFromDB.get(1).getTransmission().getName()).isEqualTo(car2.getTransmission().getName());
        assertThat(carsFromDB.get(1).getYear().getId()).isEqualTo(car2.getYear().getId());
        assertThat(carsFromDB.get(1).getYear().getYear()).isEqualTo(car2.getYear().getYear());
    }

    @Test
    public void whenUpdateThenMustBeChangedCar() {
        Engine engine1 = new Engine();
        engine1.setName("1.5/Бензин");
        engineStore.create(engine1);
        User user1 = new User();
        user1.setLogin("login");
        user1.setPassword("password");
        userStore.create(user1);
        Driver driver1 = new Driver();
        driver1.setName("Valera");
        driver1.setUser(user1);
        driverStore.create(driver1);
        CarBrand carBrand1 = new CarBrand();
        carBrand1.setName("Toyota");
        carBrandStore.create(carBrand1);
        CarBody carBody1 = new CarBody();
        carBody1.setName("Универсал");
        carBodyStore.create(carBody1);
        WheelDrive wheelDrive1 = new WheelDrive();
        wheelDrive1.setName("Полный");
        wheelDriveStore.create(wheelDrive1);
        CarColour carColour1 = new CarColour();
        carColour1.setName("Черный");
        carColourStore.create(carColour1);
        Transmission transmission1 = new Transmission();
        transmission1.setName("Автомат");
        transmissionStore.create(transmission1);
        Year year1 = new Year();
        year1.setYear(2000);
        yearStore.create(year1);
        Car car = new Car();
        car.setMileage(100000);
        car.setEngine(engine1);
        car.setDriver(driver1);
        car.setCarBrand(carBrand1);
        car.setCarBody(carBody1);
        car.setWheelDrive(wheelDrive1);
        car.setCarColour(carColour1);
        car.setTransmission(transmission1);
        car.setYear(year1);
        carStore.create(car);
        Engine engine2 = new Engine();
        engine2.setName("2.0/Дизель");
        engineStore.create(engine2);
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        userStore.create(user2);
        Driver driver2 = new Driver();
        driver2.setName("Ivan");
        driver2.setUser(user2);
        driverStore.create(driver2);
        CarBrand carBrand2 = new CarBrand();
        carBrand2.setName("KIA");
        carBrandStore.create(carBrand2);
        CarBody carBody2 = new CarBody();
        carBody2.setName("Седан");
        carBodyStore.create(carBody2);
        WheelDrive wheelDrive2 = new WheelDrive();
        wheelDrive2.setName("Задний");
        wheelDriveStore.create(wheelDrive2);
        CarColour carColour2 = new CarColour();
        carColour2.setName("Белый");
        carColourStore.create(carColour2);
        Transmission transmission2 = new Transmission();
        transmission2.setName("Механика");
        transmissionStore.create(transmission2);
        Year year2 = new Year();
        year2.setYear(2015);
        yearStore.create(year2);
        Car changedCar = new Car();
        changedCar.setMileage(70000);
        changedCar.setEngine(engine2);
        changedCar.setDriver(driver2);
        changedCar.setCarBrand(carBrand2);
        changedCar.setCarBody(carBody2);
        changedCar.setWheelDrive(wheelDrive2);
        changedCar.setCarColour(carColour2);
        changedCar.setTransmission(transmission2);
        changedCar.setYear(year2);
        changedCar.setId(car.getId());
        carStore.update(changedCar);
        Car carFromDB = carStore.findById(car.getId()).get();
        assertThat(carFromDB.getMileage()).isEqualTo(changedCar.getMileage());
        assertThat(carFromDB.getEngine().getId()).isEqualTo(changedCar.getEngine().getId());
        assertThat(carFromDB.getEngine().getName()).isEqualTo(changedCar.getEngine().getName());
        assertThat(carFromDB.getDriver().getId()).isEqualTo(changedCar.getDriver().getId());
        assertThat(carFromDB.getDriver().getName()).isEqualTo(changedCar.getDriver().getName());
        assertThat(carFromDB.getDriver().getUser().getId()).isEqualTo(changedCar.getDriver()
                .getUser().getId());
        assertThat(carFromDB.getDriver().getUser().getLogin()).isEqualTo(changedCar.getDriver()
                .getUser().getLogin());
        assertThat(carFromDB.getDriver().getUser().getPassword()).isEqualTo(changedCar.getDriver()
                .getUser().getPassword());
        assertThat(carFromDB.getCarBrand().getId()).isEqualTo(changedCar.getCarBrand().getId());
        assertThat(carFromDB.getCarBrand().getName()).isEqualTo(changedCar.getCarBrand().getName());
        assertThat(carFromDB.getCarBody().getId()).isEqualTo(changedCar.getCarBody().getId());
        assertThat(carFromDB.getCarBody().getName()).isEqualTo(changedCar.getCarBody().getName());
        assertThat(carFromDB.getWheelDrive().getId()).isEqualTo(changedCar.getWheelDrive().getId());
        assertThat(carFromDB.getWheelDrive().getName()).isEqualTo(changedCar.getWheelDrive().getName());
        assertThat(carFromDB.getCarColour().getId()).isEqualTo(changedCar.getCarColour().getId());
        assertThat(carFromDB.getCarColour().getName()).isEqualTo(changedCar.getCarColour().getName());
        assertThat(carFromDB.getTransmission().getId()).isEqualTo(changedCar.getTransmission().getId());
        assertThat(carFromDB.getTransmission().getName()).isEqualTo(changedCar.getTransmission().getName());
        assertThat(carFromDB.getYear().getId()).isEqualTo(changedCar.getYear().getId());
        assertThat(carFromDB.getYear().getYear()).isEqualTo(changedCar.getYear().getYear());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        Engine engine = new Engine();
        engine.setName("1.5/Бензин");
        engineStore.create(engine);
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        userStore.create(user);
        Driver driver = new Driver();
        driver.setName("Valera");
        driver.setUser(user);
        driverStore.create(driver);
        CarBrand carBrand = new CarBrand();
        carBrand.setName("Toyota");
        carBrandStore.create(carBrand);
        CarBody carBody = new CarBody();
        carBody.setName("Универсал");
        carBodyStore.create(carBody);
        WheelDrive wheelDrive = new WheelDrive();
        wheelDrive.setName("Полный");
        wheelDriveStore.create(wheelDrive);
        CarColour carColour = new CarColour();
        carColour.setName("Черный");
        carColourStore.create(carColour);
        Transmission transmission = new Transmission();
        transmission.setName("Автомат");
        transmissionStore.create(transmission);
        Year year = new Year();
        year.setYear(2015);
        yearStore.create(year);
        Car car = new Car();
        car.setMileage(70000);
        car.setEngine(engine);
        car.setDriver(driver);
        car.setCarBrand(carBrand);
        car.setCarBody(carBody);
        car.setWheelDrive(wheelDrive);
        car.setCarColour(carColour);
        car.setTransmission(transmission);
        car.setYear(year);
        carStore.create(car);
        carStore.delete(car.getId());
        List<Car> carsFromDB = carStore.findAll();
        assertThat(carsFromDB.isEmpty()).isTrue();
    }
}