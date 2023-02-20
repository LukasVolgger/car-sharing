package carsharing.repositories;

import carsharing.Main;
import carsharing.models.Car;
import carsharing.models.Company;
import carsharing.models.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Carsharing {
  final static String jdbcURL = "jdbc:h2:file:./src/carsharing/db/carsharing;AUTO_SERVER=TRUE";

  public static void createTables(String[] args) {
    String dbFileName = "";

    // Search for passed values in the args array
    for (int i = 0; i < args.length; i++) {
      if (args[i].contains("-databaseFileName")) {
        dbFileName  = args[i].replace("-databaseFileName", "");
      } else {
        dbFileName = "carsharing";
      }
    }

    // Needed because hyperskill passes the args
    if (args.length < 1) {
      dbFileName = "carsharing";
    }

    String jdbcURL = "jdbc:h2:file:./src/carsharing/db/" + dbFileName + ";AUTO_SERVER=TRUE";

    // Credentials currently unused
    String username = "";
    String password = "";

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      System.out.println("Connected to database!");
      System.out.println("Creating table...");

      String sql = ""
          + "CREATE TABLE IF NOT EXISTS COMPANY  ( \n"
          + "   ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, \n"
          + "   NAME VARCHAR(50) NOT NULL UNIQUE \n"
          + ");"
          + "CREATE TABLE IF NOT EXISTS CAR  ( \n"
          + "   ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, \n"
          + "   NAME VARCHAR(50) NOT NULL UNIQUE, \n"
          + "   COMPANY_ID INT NOT NULL, \n"
          + "   FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)"
          + ");"
          + "CREATE TABLE IF NOT EXISTS CUSTOMER  ( \n"
          + "   ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, \n"
          + "   NAME VARCHAR(50) NOT NULL UNIQUE, \n"
          + "   RENTED_CAR_ID INT DEFAULT NULL, \n"
          + "   FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)"
          + ");";

      Statement statement = connection.createStatement();
      statement.execute(sql);

      System.out.println("Tables successfully created!");
      System.out.println("----------------------------");

      connection.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void createCompany() {
    System.out.println("Enter the company name:");
    String input = new Scanner(System.in).nextLine();

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      String query = "INSERT INTO COMPANY (NAME) VALUES (?)";
      PreparedStatement stmt = connection.prepareStatement(query);

      stmt.setString(1, input);
      stmt.execute();

      connection.close();

      System.out.println("Company \"" + input + "\" successfully created!");

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  public static Company getCompany(int ID) {

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      Statement stmt = connection.createStatement();
      String sql = "SELECT * FROM COMPANY WHERE ID = " + ID + ";";

      ResultSet rst = stmt.executeQuery(sql);
      rst.next();

      Company company = new Company(rst.getInt("ID"), rst.getString("NAME"));

      connection.close();

      return company;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static ArrayList<Company> getCompanyList() {

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      Statement stm = connection.createStatement();

      String sql = "SELECT * FROM COMPANY";
      ResultSet rst = stm.executeQuery(sql);

      ArrayList<Company> companyList = new ArrayList<>();

      while (rst.next()) {
        Company company = new Company(rst.getInt("ID"), rst.getString("NAME"));
        companyList.add(company);
      }

      connection.close();
      return companyList;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  public static void createCustomer() {
    System.out.println("Enter the customer name:");
    String input = new Scanner(System.in).nextLine();

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      String query = "INSERT INTO CUSTOMER (NAME) VALUES (?)";
      PreparedStatement stmt = connection.prepareStatement(query);

      stmt.setString(1, input);
      stmt.execute();

      connection.close();

      System.out.println("Customer \"" + input + "\" successfully created!");

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static Customer getCustomer(int ID) {

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      Statement stmt = connection.createStatement();
      String sql = "SELECT * FROM CUSTOMER WHERE ID = " + ID + ";";

      ResultSet rst = stmt.executeQuery(sql);
      rst.next();

      Customer customer = new Customer(rst.getInt("ID"), rst.getString("NAME"), rst.getInt("RENTED_CAR_ID"));

      connection.close();

      return customer;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static ArrayList<Customer> getCustomerList() {

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      Statement stm = connection.createStatement();

      String sql = "SELECT * FROM CUSTOMER";
      ResultSet rst = stm.executeQuery(sql);

      ArrayList<Customer> customerList = new ArrayList<>();

      while (rst.next()) {
        Customer customer = new Customer(rst.getInt("ID"), rst.getString("NAME"), rst.getInt("RENTED_CAR_ID"));
        customerList.add(customer);
      }

      connection.close();
      return customerList;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  public static void rentCar(int customerID, int carID) {

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      Statement stmt = connection.createStatement();
      String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = " + carID + " WHERE ID = " + customerID + ";";

      stmt.execute(sql);
      connection.close();

      System.out.println("You rented '" + getCar(carID).NAME + "'");
      Main.customerMenu(getCustomer(customerID));

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public  static void returnRentedCar(int ID) {

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      Statement stmt = connection.createStatement();
      String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = " + ID + ";";

      stmt.execute(sql);
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void createCar(int companyID) {
    System.out.println("Enter the car name:");
    String input = new Scanner(System.in).nextLine();

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      String query = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)";
      PreparedStatement stmt = connection.prepareStatement(query);

      stmt.setString(1, input);
      stmt.setInt(2, companyID);

      stmt.execute();

      connection.close();

      System.out.println("Car \"" + input + "\" successfully created!");

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  public static Car getCar(int ID) {
    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      Statement stmt = connection.createStatement();
      String sql = "SELECT * FROM CAR WHERE ID = " + ID + ";";

      ResultSet rst = stmt.executeQuery(sql);
      rst.next();

      Car car = new Car(rst.getInt("ID"), rst.getString("NAME"), rst.getInt("COMPANY_ID"));

      connection.close();

      return car;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static ArrayList<Car> getCarList(int companyID) {

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      Statement stm = connection.createStatement();

      String sql = "SELECT * FROM CAR WHERE COMPANY_ID = " + companyID + ";";
      ResultSet rst = stm.executeQuery(sql);

      ArrayList<Car> carList = new ArrayList<>();

      while (rst.next()) {
        Car car = new Car(rst.getInt("ID"), rst.getString("NAME"), rst.getInt("COMPANY_ID"));
        carList.add(car);
      }

      connection.close();
      return carList;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  public static ArrayList<Car> getAvailableCarList(int companyID) {

    try {
      Connection connection = DriverManager.getConnection(jdbcURL);
      connection.setAutoCommit(true);

      Statement stm = connection.createStatement();

      String sql = "SELECT * FROM CAR WHERE COMPANY_ID = " + companyID + ""
          + " AND ID NOT IN ( \n"
          + " SELECT RENTED_CAR_ID \n"
          + " FROM CUSTOMER \n"
          + " WHERE RENTED_CAR_ID IS NOT NULL);";
      ResultSet rst = stm.executeQuery(sql);

      ArrayList<Car> carList = new ArrayList<>();

      while (rst.next()) {
        Car car = new Car(rst.getInt("ID"), rst.getString("NAME"), rst.getInt("COMPANY_ID"));
        carList.add(car);
      }

      connection.close();
      return carList;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }


}
