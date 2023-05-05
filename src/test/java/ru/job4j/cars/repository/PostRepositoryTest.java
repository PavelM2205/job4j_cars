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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PostRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository cr = new CrudRepository(sf);
    private final EngineRepository engineStore = new EngineRepository(cr);
    private final DriverRepository driverStore = new DriverRepository(cr);
    private final UserRepository userStore = new UserRepository(cr);
    private final CarBrandRepository carBrandStore = new CarBrandRepository(cr);
    private final CarBodyRepository carBodyStore = new CarBodyRepository(cr);
    private final WheelDriveRepository wheelDriveStore = new WheelDriveRepository(cr);
    private final CarColourRepository carColourStore = new CarColourRepository(cr);
    private final TransmissionRepository transmissionStore = new TransmissionRepository(cr);
    private final YearRepository yearStore = new YearRepository(cr);
    private final PostRepository postStore = new PostRepository(cr);
    private final CityRepository cityStore = new CityRepository(cr);

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
            session.beginTransaction();
            session.createMutationQuery("DELETE Post").executeUpdate();
            session.createMutationQuery("DELETE Car").executeUpdate();
            session.createMutationQuery("DELETE Driver").executeUpdate();
            session.createMutationQuery("DELETE CarBrand").executeUpdate();
            session.createMutationQuery("DELETE CarBody").executeUpdate();
            session.createMutationQuery("DELETE WheelDrive").executeUpdate();
            session.createMutationQuery("DELETE CarColour").executeUpdate();
            session.createMutationQuery("DELETE Transmission").executeUpdate();
            session.createMutationQuery("DELETE Year").executeUpdate();
            session.createMutationQuery("DELETE City").executeUpdate();
            session.createMutationQuery("DELETE Engine").executeUpdate();
            session.createMutationQuery("DELETE User").executeUpdate();
            transaction = session.getTransaction();
            transaction.commit();
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
    public void whenCreateThenFindByIdReturnsPostWithNotNullId() {
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
        City city = new City();
        city.setName("Красноярск");
        cityStore.create(city);
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post.setCar(car);
        post.setPhoto(new byte[1]);
        post.setCity(city);
        post.setPrice(100);
        post.setSold(true);
        postStore.create(post);
        Post postFromDB = postStore.findById(post.getId()).get();
        assertThat(postFromDB.getText()).isEqualTo(post.getText());
        assertThat(postFromDB.getUser().getId()).isEqualTo(post.getUser().getId());
        assertThat(postFromDB.getUser().getLogin()).isEqualTo(post.getUser().getLogin());
        assertThat(postFromDB.getUser().getPassword()).isEqualTo(post.getUser().getPassword());
        assertThat(postFromDB.getPhoto()).isEqualTo(post.getPhoto());
        assertThat(postFromDB.getCity().getId()).isEqualTo(post.getCity().getId());
        assertThat(postFromDB.getCity().getName()).isEqualTo(post.getCity().getName());
        assertThat(postFromDB.getPrice()).isEqualTo(post.getPrice());
        assertThat(postFromDB.isSold()).isEqualTo(post.isSold());
        assertThat(postFromDB.getCar().getId()).isEqualTo(post.getCar().getId());
        assertThat(postFromDB.getCar().getMileage()).isEqualTo(post.getCar().getMileage());
        assertThat(postFromDB.getCar().getEngine().getId()).isEqualTo(post.getCar().getEngine().getId());
        assertThat(postFromDB.getCar().getEngine().getName()).isEqualTo(post.getCar().getEngine().getName());
        assertThat(postFromDB.getCar().getDriver().getId()).isEqualTo(post.getCar().getDriver().getId());
        assertThat(postFromDB.getCar().getDriver().getName()).isEqualTo(post.getCar().getDriver().getName());
        assertThat(postFromDB.getCar().getDriver().getUser().getId()).isEqualTo(post.getCar().getDriver()
                .getUser().getId());
        assertThat(postFromDB.getCar().getDriver().getUser().getLogin()).isEqualTo(post.getCar().getDriver()
                .getUser().getLogin());
        assertThat(postFromDB.getCar().getDriver().getUser().getPassword()).isEqualTo(post.getCar().getDriver()
                .getUser().getPassword());
        assertThat(postFromDB.getCar().getCarBrand().getId()).isEqualTo(post.getCar().getCarBrand().getId());
        assertThat(postFromDB.getCar().getCarBrand().getName()).isEqualTo(post.getCar().getCarBrand().getName());
        assertThat(postFromDB.getCar().getCarBody().getId()).isEqualTo(post.getCar().getCarBody().getId());
        assertThat(postFromDB.getCar().getCarBody().getName()).isEqualTo(post.getCar().getCarBody().getName());
        assertThat(postFromDB.getCar().getWheelDrive().getId()).isEqualTo(post.getCar().getWheelDrive().getId());
        assertThat(postFromDB.getCar().getWheelDrive().getName()).isEqualTo(post.getCar().getWheelDrive().getName());
        assertThat(postFromDB.getCar().getCarColour().getId()).isEqualTo(post.getCar().getCarColour().getId());
        assertThat(postFromDB.getCar().getCarColour().getName()).isEqualTo(post.getCar().getCarColour().getName());
        assertThat(postFromDB.getCar().getTransmission().getId()).isEqualTo(post.getCar().getTransmission().getId());
        assertThat(postFromDB.getCar().getTransmission().getName()).isEqualTo(post.getCar().getTransmission().getName());
        assertThat(postFromDB.getCar().getYear().getId()).isEqualTo(post.getCar().getYear().getId());
        assertThat(postFromDB.getCar().getYear().getYear()).isEqualTo(post.getCar().getYear().getYear());
    }

    @Test
    public void whenAddTwoPostThenFindAllReturnsBoth() {
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
        year1.setYear(2015);
        yearStore.create(year1);
        Car car1 = new Car();
        car1.setMileage(70000);
        car1.setEngine(engine1);
        car1.setDriver(driver1);
        car1.setCarBrand(carBrand1);
        car1.setCarBody(carBody1);
        car1.setWheelDrive(wheelDrive1);
        car1.setCarColour(carColour1);
        car1.setTransmission(transmission1);
        car1.setYear(year1);
        City city1 = new City();
        city1.setName("Красноярск");
        cityStore.create(city1);
        Post post1 = new Post();
        post1.setText("text");
        post1.setUser(user1);
        post1.setCar(car1);
        post1.setPhoto(new byte[1]);
        post1.setCity(city1);
        post1.setPrice(100);
        post1.setSold(true);
        postStore.create(post1);
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
        carBrand2.setName("BMW");
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
        year2.setYear(2000);
        yearStore.create(year2);
        Car car2 = new Car();
        car2.setMileage(100000);
        car2.setEngine(engine2);
        car2.setDriver(driver2);
        car2.setCarBrand(carBrand2);
        car2.setCarBody(carBody2);
        car2.setWheelDrive(wheelDrive2);
        car2.setCarColour(carColour2);
        car2.setTransmission(transmission2);
        car2.setYear(year2);
        City city2 = new City();
        city2.setName("Новосибирск");
        cityStore.create(city2);
        Post post2 = new Post();
        post2.setText("text_2");
        post2.setUser(user2);
        post2.setCar(car2);
        post2.setPhoto(new byte[2]);
        post2.setCity(city2);
        post2.setPrice(500);
        post2.setSold(false);
        postStore.create(post2);
        List<Post> postsFromDB = postStore.findAll();
        assertThat(postsFromDB.get(0).getText()).isEqualTo(post1.getText());
        assertThat(postsFromDB.get(0).getUser().getId()).isEqualTo(post1.getUser().getId());
        assertThat(postsFromDB.get(0).getUser().getLogin()).isEqualTo(post1.getUser().getLogin());
        assertThat(postsFromDB.get(0).getUser().getPassword()).isEqualTo(post1.getUser().getPassword());
        assertThat(postsFromDB.get(0).getPhoto()).isEqualTo(post1.getPhoto());
        assertThat(postsFromDB.get(0).getCity().getId()).isEqualTo(post1.getCity().getId());
        assertThat(postsFromDB.get(0).getCity().getName()).isEqualTo(post1.getCity().getName());
        assertThat(postsFromDB.get(0).getPrice()).isEqualTo(post1.getPrice());
        assertThat(postsFromDB.get(0).isSold()).isEqualTo(post1.isSold());
        assertThat(postsFromDB.get(0).getCar().getId()).isEqualTo(post1.getCar().getId());
        assertThat(postsFromDB.get(0).getCar().getMileage()).isEqualTo(post1.getCar().getMileage());
        assertThat(postsFromDB.get(0).getCar().getEngine().getId()).isEqualTo(post1.getCar().getEngine().getId());
        assertThat(postsFromDB.get(0).getCar().getEngine().getName()).isEqualTo(post1.getCar().getEngine().getName());
        assertThat(postsFromDB.get(0).getCar().getDriver().getId()).isEqualTo(post1.getCar().getDriver().getId());
        assertThat(postsFromDB.get(0).getCar().getDriver().getName()).isEqualTo(post1.getCar().getDriver().getName());
        assertThat(postsFromDB.get(0).getCar().getDriver().getUser().getId()).isEqualTo(post1.getCar().getDriver()
                .getUser().getId());
        assertThat(postsFromDB.get(0).getCar().getDriver().getUser().getLogin()).isEqualTo(post1.getCar().getDriver()
                .getUser().getLogin());
        assertThat(postsFromDB.get(0).getCar().getDriver().getUser().getPassword()).isEqualTo(post1.getCar().getDriver()
                .getUser().getPassword());
        assertThat(postsFromDB.get(0).getCar().getCarBrand().getId()).isEqualTo(post1.getCar().getCarBrand().getId());
        assertThat(postsFromDB.get(0).getCar().getCarBrand().getName()).isEqualTo(post1.getCar().getCarBrand().getName());
        assertThat(postsFromDB.get(0).getCar().getCarBody().getId()).isEqualTo(post1.getCar().getCarBody().getId());
        assertThat(postsFromDB.get(0).getCar().getCarBody().getName()).isEqualTo(post1.getCar().getCarBody().getName());
        assertThat(postsFromDB.get(0).getCar().getWheelDrive().getId()).isEqualTo(post1.getCar().getWheelDrive().getId());
        assertThat(postsFromDB.get(0).getCar().getWheelDrive().getName()).isEqualTo(post1.getCar().getWheelDrive().getName());
        assertThat(postsFromDB.get(0).getCar().getCarColour().getId()).isEqualTo(post1.getCar().getCarColour().getId());
        assertThat(postsFromDB.get(0).getCar().getCarColour().getName()).isEqualTo(post1.getCar().getCarColour().getName());
        assertThat(postsFromDB.get(0).getCar().getTransmission().getId()).isEqualTo(post1.getCar().getTransmission().getId());
        assertThat(postsFromDB.get(0).getCar().getTransmission().getName()).isEqualTo(post1.getCar().getTransmission().getName());
        assertThat(postsFromDB.get(0).getCar().getYear().getId()).isEqualTo(post1.getCar().getYear().getId());
        assertThat(postsFromDB.get(0).getCar().getYear().getYear()).isEqualTo(post1.getCar().getYear().getYear());
        assertThat(postsFromDB.get(1).getText()).isEqualTo(post2.getText());
        assertThat(postsFromDB.get(1).getUser().getId()).isEqualTo(post2.getUser().getId());
        assertThat(postsFromDB.get(1).getUser().getLogin()).isEqualTo(post2.getUser().getLogin());
        assertThat(postsFromDB.get(1).getUser().getPassword()).isEqualTo(post2.getUser().getPassword());
        assertThat(postsFromDB.get(1).getPhoto()).isEqualTo(post2.getPhoto());
        assertThat(postsFromDB.get(1).getCity().getId()).isEqualTo(post2.getCity().getId());
        assertThat(postsFromDB.get(1).getCity().getName()).isEqualTo(post2.getCity().getName());
        assertThat(postsFromDB.get(1).getPrice()).isEqualTo(post2.getPrice());
        assertThat(postsFromDB.get(1).isSold()).isEqualTo(post2.isSold());
        assertThat(postsFromDB.get(1).getCar().getId()).isEqualTo(post2.getCar().getId());
        assertThat(postsFromDB.get(1).getCar().getMileage()).isEqualTo(post2.getCar().getMileage());
        assertThat(postsFromDB.get(1).getCar().getEngine().getId()).isEqualTo(post2.getCar().getEngine().getId());
        assertThat(postsFromDB.get(1).getCar().getEngine().getName()).isEqualTo(post2.getCar().getEngine().getName());
        assertThat(postsFromDB.get(1).getCar().getDriver().getId()).isEqualTo(post2.getCar().getDriver().getId());
        assertThat(postsFromDB.get(1).getCar().getDriver().getName()).isEqualTo(post2.getCar().getDriver().getName());
        assertThat(postsFromDB.get(1).getCar().getDriver().getUser().getId()).isEqualTo(post2.getCar().getDriver()
                .getUser().getId());
        assertThat(postsFromDB.get(1).getCar().getDriver().getUser().getLogin()).isEqualTo(post2.getCar().getDriver()
                .getUser().getLogin());
        assertThat(postsFromDB.get(1).getCar().getDriver().getUser().getPassword()).isEqualTo(post2.getCar().getDriver()
                .getUser().getPassword());
        assertThat(postsFromDB.get(1).getCar().getCarBrand().getId()).isEqualTo(post2.getCar().getCarBrand().getId());
        assertThat(postsFromDB.get(1).getCar().getCarBrand().getName()).isEqualTo(post2.getCar().getCarBrand().getName());
        assertThat(postsFromDB.get(1).getCar().getCarBody().getId()).isEqualTo(post2.getCar().getCarBody().getId());
        assertThat(postsFromDB.get(1).getCar().getCarBody().getName()).isEqualTo(post2.getCar().getCarBody().getName());
        assertThat(postsFromDB.get(1).getCar().getWheelDrive().getId()).isEqualTo(post2.getCar().getWheelDrive().getId());
        assertThat(postsFromDB.get(1).getCar().getWheelDrive().getName()).isEqualTo(post2.getCar().getWheelDrive().getName());
        assertThat(postsFromDB.get(1).getCar().getCarColour().getId()).isEqualTo(post2.getCar().getCarColour().getId());
        assertThat(postsFromDB.get(1).getCar().getCarColour().getName()).isEqualTo(post2.getCar().getCarColour().getName());
        assertThat(postsFromDB.get(1).getCar().getTransmission().getId()).isEqualTo(post2.getCar().getTransmission().getId());
        assertThat(postsFromDB.get(1).getCar().getTransmission().getName()).isEqualTo(post2.getCar().getTransmission().getName());
        assertThat(postsFromDB.get(1).getCar().getYear().getId()).isEqualTo(post2.getCar().getYear().getId());
        assertThat(postsFromDB.get(1).getCar().getYear().getYear()).isEqualTo(post2.getCar().getYear().getYear());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedPost() {
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
        year1.setYear(2015);
        yearStore.create(year1);
        Car car1 = new Car();
        car1.setMileage(70000);
        car1.setEngine(engine1);
        car1.setDriver(driver1);
        car1.setCarBrand(carBrand1);
        car1.setCarBody(carBody1);
        car1.setWheelDrive(wheelDrive1);
        car1.setCarColour(carColour1);
        car1.setTransmission(transmission1);
        car1.setYear(year1);
        City city1 = new City();
        city1.setName("Красноярск");
        cityStore.create(city1);
        Post post = new Post();
        post.setText("text");
        post.setUser(user1);
        post.setCar(car1);
        post.setPhoto(new byte[1]);
        post.setCity(city1);
        post.setPrice(100);
        post.setSold(true);
        postStore.create(post);
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
        carBrand2.setName("BMW");
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
        year2.setYear(2000);
        yearStore.create(year2);
        Car car2 = new Car();
        car2.setMileage(100000);
        car2.setEngine(engine2);
        car2.setDriver(driver2);
        car2.setCarBrand(carBrand2);
        car2.setCarBody(carBody2);
        car2.setWheelDrive(wheelDrive2);
        car2.setCarColour(carColour2);
        car2.setTransmission(transmission2);
        car2.setYear(year2);
        City city2 = new City();
        city2.setName("Новосибирск");
        cityStore.create(city2);
        Post changedPost = new Post();
        changedPost.setText("text_2");
        changedPost.setUser(user2);
        changedPost.setCar(car2);
        changedPost.setPhoto(new byte[2]);
        changedPost.setCity(city2);
        changedPost.setPrice(500);
        changedPost.setSold(false);
        changedPost.setId(post.getId());
        postStore.update(changedPost);
        Post postFromDB = postStore.findById(post.getId()).get();
        assertThat(postFromDB.getText()).isEqualTo(changedPost.getText());
        assertThat(postFromDB.getUser().getId()).isEqualTo(changedPost.getUser().getId());
        assertThat(postFromDB.getUser().getLogin()).isEqualTo(changedPost.getUser().getLogin());
        assertThat(postFromDB.getUser().getPassword()).isEqualTo(changedPost.getUser().getPassword());
        assertThat(postFromDB.getPhoto()).isEqualTo(changedPost.getPhoto());
        assertThat(postFromDB.getCity().getId()).isEqualTo(changedPost.getCity().getId());
        assertThat(postFromDB.getCity().getName()).isEqualTo(changedPost.getCity().getName());
        assertThat(postFromDB.getPrice()).isEqualTo(changedPost.getPrice());
        assertThat(postFromDB.isSold()).isEqualTo(changedPost.isSold());
        assertThat(postFromDB.getCar().getId()).isEqualTo(changedPost.getCar().getId());
        assertThat(postFromDB.getCar().getMileage()).isEqualTo(changedPost.getCar().getMileage());
        assertThat(postFromDB.getCar().getEngine().getId()).isEqualTo(changedPost.getCar().getEngine().getId());
        assertThat(postFromDB.getCar().getEngine().getName()).isEqualTo(changedPost.getCar().getEngine().getName());
        assertThat(postFromDB.getCar().getDriver().getId()).isEqualTo(changedPost.getCar().getDriver().getId());
        assertThat(postFromDB.getCar().getDriver().getName()).isEqualTo(changedPost.getCar().getDriver().getName());
        assertThat(postFromDB.getCar().getDriver().getUser().getId()).isEqualTo(changedPost.getCar().getDriver()
                .getUser().getId());
        assertThat(postFromDB.getCar().getDriver().getUser().getLogin()).isEqualTo(changedPost.getCar().getDriver()
                .getUser().getLogin());
        assertThat(postFromDB.getCar().getDriver().getUser().getPassword()).isEqualTo(changedPost.getCar().getDriver()
                .getUser().getPassword());
        assertThat(postFromDB.getCar().getCarBrand().getId()).isEqualTo(changedPost.getCar().getCarBrand().getId());
        assertThat(postFromDB.getCar().getCarBrand().getName()).isEqualTo(changedPost.getCar().getCarBrand().getName());
        assertThat(postFromDB.getCar().getCarBody().getId()).isEqualTo(changedPost.getCar().getCarBody().getId());
        assertThat(postFromDB.getCar().getCarBody().getName()).isEqualTo(changedPost.getCar().getCarBody().getName());
        assertThat(postFromDB.getCar().getWheelDrive().getId()).isEqualTo(changedPost.getCar().getWheelDrive().getId());
        assertThat(postFromDB.getCar().getWheelDrive().getName()).isEqualTo(changedPost.getCar().getWheelDrive().getName());
        assertThat(postFromDB.getCar().getCarColour().getId()).isEqualTo(changedPost.getCar().getCarColour().getId());
        assertThat(postFromDB.getCar().getCarColour().getName()).isEqualTo(changedPost.getCar().getCarColour().getName());
        assertThat(postFromDB.getCar().getTransmission().getId()).isEqualTo(changedPost.getCar().getTransmission().getId());
        assertThat(postFromDB.getCar().getTransmission().getName()).isEqualTo(changedPost.getCar().getTransmission().getName());
        assertThat(postFromDB.getCar().getYear().getId()).isEqualTo(changedPost.getCar().getYear().getId());
        assertThat(postFromDB.getCar().getYear().getYear()).isEqualTo(changedPost.getCar().getYear().getYear());
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
        City city = new City();
        city.setName("Красноярск");
        cityStore.create(city);
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post.setCar(car);
        post.setPhoto(new byte[1]);
        post.setCity(city);
        post.setPrice(100);
        post.setSold(true);
        postStore.create(post);
        postStore.delete(post.getId());
        List<Post> postsFromDB = postStore.findAll();
        assertThat(postsFromDB.isEmpty()).isTrue();
    }

    @Test
    public void whenFindCarsForCurrentDate() {
        Post currentDatePost = new Post();
        currentDatePost.setCreated(LocalDateTime.now());
        Post minusDayPost = new Post();
        minusDayPost.setCreated(LocalDateTime.now().minusDays(1));
        postStore.create(currentDatePost);
        postStore.create(minusDayPost);
        List<Post> postsFromDB = postStore.findPostsForCurrentDate();
        assertThat(postsFromDB.size()).isEqualTo(1);
        assertThat(postsFromDB.get(0).getId()).isEqualTo(currentDatePost.getId());
    }

    @Test
    public void whenFindPostsWithPhoto() {
        Post withPhotoPost = new Post();
        withPhotoPost.setPhoto(new byte[1]);
        Post withoutPhotoPost = new Post();
        postStore.create(withPhotoPost);
        postStore.create(withoutPhotoPost);
        List<Post> postsFromDB = postStore.findPostsWithPhoto();
        assertThat(postsFromDB.size()).isEqualTo(1);
        assertThat(postsFromDB.get(0).getId()).isEqualTo(withPhotoPost.getId());
    }

    @Test
    public void whenFindPostsWithDefiniteMakeOfCa() {
        Post post1 = new Post();
        Car car1 = new Car();
        CarBrand carBrand1 = new CarBrand();
        carBrand1.setName("Toyota");
        carBrandStore.create(carBrand1);
        car1.setCarBrand(carBrand1);
        post1.setCar(car1);
        postStore.create(post1);
        Post post2 = new Post();
        Car car2 = new Car();
        CarBrand carBrand2 = new CarBrand();
        carBrand2.setName("BMW");
        carBrandStore.create(carBrand2);
        car2.setCarBrand(carBrand2);
        post2.setCar(car2);
        postStore.create(post2);
        List<Post> postsFromDB = postStore.findPostsWithDefiniteMakeOfCar("BMW");
        assertThat(postsFromDB.size()).isEqualTo(1);
        assertThat(postsFromDB.get(0).getId()).isEqualTo(post2.getId());
    }

    @Test
    public void whenFindPostsForDefiniteUser() {
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        userStore.create(user1);
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        userStore.create(user2);
        Post post1 = new Post();
        post1.setUser(user1);
        Post post2 = new Post();
        post2.setUser(user1);
        Post post3 = new Post();
        post3.setUser(user2);
        postStore.create(post1);
        postStore.create(post2);
        postStore.create(post3);
        List<Post> postsFromDB = postStore.findPostsForDefiniteUser(user1.getId());
        assertThat(postsFromDB.size()).isEqualTo(2);
        assertThat(postsFromDB.get(0).getUser().getId()).isEqualTo(user1.getId());
        assertThat(postsFromDB.get(1).getUser().getId()).isEqualTo(user1.getId());
    }
}
