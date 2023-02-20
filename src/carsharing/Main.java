package carsharing;

import carsharing.models.Car;
import carsharing.models.Company;
import carsharing.models.Customer;
import carsharing.repositories.Carsharing;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Carsharing.createTables(args);
    mainMenu();
  }

  public static void mainMenu() {
    boolean exit = false;

    while (!exit) {
      System.out.println("1. Log in as a manager");
      System.out.println("2. Log in as a customer");
      System.out.println("3. Create a customer");
      System.out.println("0. Exit");

      String input = new Scanner(System.in).nextLine();

      switch (input) {
        case "0" -> {
          return;
        }
        case "1" -> managerMenu();
        case "2" -> listCustomers();
        case "3" -> Carsharing.createCustomer();
        case "4" -> debug();
        default -> System.out.println("Invalid input!");
      }
    }
  }

  public static void debug() {
    ArrayList<Car> carList = Carsharing.getCarList(1);
    ArrayList<Customer> customerList = Carsharing.getCustomerList();

    System.out.println("Cars:");
    for (Car car : carList) {
      System.out.println("ID: " + car.ID + " Name: " + car.NAME + " COMPANY: " + car.COMPANY_ID);
    }

    System.out.println("Customers:");
    for (Customer customer : customerList) {
      System.out.println("ID: " + customer.ID + " Name: " + customer.NAME + " CAR: " + customer.RENTED_CAR_ID);
    }
  }

  public static void managerMenu() {
    boolean exit = false;

    while (!exit) {
      System.out.println("1. Company list");
      System.out.println("2. Create a company");
      System.out.println("0. Back");

      String input = new Scanner(System.in).nextLine();

      switch (input) {
        case "0" -> {
          exit = true;
          mainMenu();
        }
        case "1" -> listCompanies();
        case "2" -> Carsharing.createCompany();
        default -> System.out.println("Invalid input!");
      }
    }

  }

  public static void listCustomers() {
    ArrayList<Customer> list = Carsharing.getCustomerList();
    boolean exit = false;

    if (list.size() < 1) {
      System.out.println("The customer list is empty!");
      mainMenu();
    } else {
      int id = 1;

      while (!exit) {
        for (Customer customer : list) {
          System.out.println(id + ". " + customer.NAME);
          id++;
        }
        System.out.println("0. Back");

        System.out.println("Choose the customer: ");
        String input = new Scanner(System.in).nextLine();

        if (input.equals("0")) {
          exit = true;
          mainMenu();
        } else {
          Customer customer = Carsharing.getCustomer(Integer.parseInt(input));
          customerMenu(customer);
        }
      }
    }
  }

  public static void customerMenu(Customer customer) {
    boolean exit = false;

    while (!exit) {
      System.out.println("'" + customer.NAME + "' customer");
      System.out.println("1. Rent a car");
      System.out.println("2. Return a rented car");
      System.out.println("3. My rented car");
      System.out.println("0. Back");

      String input = new Scanner(System.in).nextLine();

      switch (input) {
        case "0" -> {
          exit = true;
          mainMenu();
        }
        case "1" -> rentCar(customer);
        case "2" -> returnRentedCar(customer);
        case "3" -> showRentedCar(customer);
        default -> System.out.println("Invalid input!");
      }
    }

  }

  public static void rentCar(Customer customer) {
    ArrayList<Company> companyList = Carsharing.getCompanyList();

    if (customer.RENTED_CAR_ID > 0) {
      System.out.println("You've already rented a car!");
      customerMenu(customer);

    } else {
      if (companyList.size() > 0) {
        System.out.println("Choose a company");

        for (Company company : companyList) {
          System.out.println(company.ID + ". " + company.NAME);
        }

        System.out.println("0. Back");

        String input = new Scanner(System.in).nextLine();

        if (input.equals("0")) {
          customerMenu(customer);
        } else {
          Company company = Carsharing.getCompany(Integer.parseInt(input));
          ArrayList<Car> carList = Carsharing.getAvailableCarList(company.ID);


          if (!(carList.size() < 1)) {

            System.out.println("Choose a car:");

            for (Car car : carList) {
              System.out.println(car.ID + ". " + car.NAME);
            }
            System.out.println("0. Back");

            String input2 = new Scanner(System.in).nextLine();

            if (input2.equals("0")) {
              customerMenu(customer);
            } else {
              Car car = Carsharing.getCar(Integer.parseInt(input2));

              Carsharing.rentCar(customer.ID, car.ID);

            }

          } else {
            System.out.println("No available cars in the '" + company.NAME + "' company");
          }
        }

      } else {
        System.out.println("The company list ist empty!");
        customerMenu(customer);
      }
    }

  }

  public static void showRentedCar(Customer customer) {

    if (customer.RENTED_CAR_ID != 0) {
      Car car = Carsharing.getCar(customer.RENTED_CAR_ID);
      Company company = Carsharing.getCompany(car.COMPANY_ID);

      System.out.println("Your rented car:");
      System.out.println(Carsharing.getCar(car.ID).NAME);
      System.out.println("Company:");
      System.out.println(Carsharing.getCompany(company.ID).NAME);
    } else {
      System.out.println("You didn't rent a car!");
    }
  }

  public static void returnRentedCar(Customer customer) {
    if (customer.RENTED_CAR_ID > 0) {
      Carsharing.returnRentedCar(customer.ID);
      System.out.println("You've returned a rented car!");
    } else {
      System.out.println("You didn't rent a car!");
    }
  }

  public static void listCompanies() {
    ArrayList<Company> list = Carsharing.getCompanyList();
    boolean exit = false;

    if (list.size() < 1) {
      System.out.println("The company list is empty!");
    } else {
      int id = 1;

      while (!exit) {
        for (Company company : list) {
          System.out.println(id + ". " + company.NAME);
          id++;
        }
        System.out.println("0. Back");

        System.out.println("Choose the company: ");
        String input = new Scanner(System.in).nextLine();

        if (input.equals("0")) {
          exit = true;
          managerMenu();
        } else {
          Company company = Carsharing.getCompany(Integer.parseInt(input));
          companyMenu(company);
        }
      }
    }

  }

  public static void companyMenu(Company company) {
    boolean exit = false;

    while (!exit) {
      System.out.println("'" + company.getNAME() + "' company");
      System.out.println("1. Car list");
      System.out.println("2. Create a car");
      System.out.println("0. Back");

      String input = new Scanner(System.in).nextLine();

      switch (input) {
        case "0" -> {
          exit = true;
          managerMenu();
        }
        case "1" -> listCars(company);
        case "2" -> Carsharing.createCar(company.getID());
        default -> System.out.println("Invalid input!");
      }
    }

  }

  public static void listCars(Company company) {
    ArrayList<Car> list = Carsharing.getCarList(company.getID());

    if (list.size() < 1) {
      System.out.println("The car list is empty!");
      companyMenu(company);
    } else {
      int id = 1;
      System.out.println("Car list: ");
      for (Car car : list) {
        System.out.println(id + ". " + car.getNAME());
        id++;
      }
    }

  }
}