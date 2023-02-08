package peaksoft;

import peaksoft.config.Util;
import peaksoft.entity.Address;
import peaksoft.entity.Country;
import peaksoft.entity.Programmer;
import peaksoft.entity.Project;
import peaksoft.enums.Status;
import peaksoft.exceptions.BadrequestException;
import peaksoft.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class Test {
    static final CountryService countryService = new CountryServiceImpl();
    static final AddressService addressService = new AddressServiceImpl();
    static final ProgrammerService programmerService = new ProgrammerServiceImpl();
    static final ProjectService projectService = new ProjectServiceImpl();

    public static void main(String[] args) throws BadrequestException {
        Util.getEntityManagerFactory();

        List<Country> countryList = new ArrayList<>(Arrays.asList(
                new Country("Kyrgyzstan", "Beautiful country"),
                new Country("Kazakhstan", "Beautiful"),
                new Country("Uzbekistan", "top")
        ));

        List<Address> addressList = new ArrayList<>(Arrays.asList(
                new Address("Talas", "Talas", 9),
                new Address("Naryn", "Vostok-5", 9),
                new Address("Osh", "Kurmanjan datka", 9)

        ));

        List<Programmer> programmerList = new ArrayList<>(Arrays.asList(
                new Programmer("Zholdoshov Nuradil", "nuradil936@gmail.com", LocalDate.of(2004, 3, 26), Status.OWNER),
                new Programmer("Shabdanob Ilim", "i@gmail.com", LocalDate.of(2003, 6, 16), Status.OWNER),
                new Programmer("Akbaraliev Dastan", "d@gmail.com", LocalDate.of(2003, 7, 6), Status.OWNER)
        ));

        List<Project> projectList = new ArrayList<>(Arrays.asList(
                new Project("Facebook", "perfect", LocalDate.of(2022, 3, 6), LocalDate.of(2023, 1, 5), BigDecimal.valueOf(10000)),
                new Project("Apple", "perfect project", LocalDate.of(2022, 8, 7), LocalDate.of(2023, 2, 7), BigDecimal.valueOf(20000)),
                new Project("Yoowe", "perfect", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 23), BigDecimal.valueOf(10000))
        ));

        while (true) {
            System.out.println("""
                    1 COUNTRY
                    2 ADDRESS
                    3 PROGRAMMER
                    4 PROJECT
                    """);
            int i = new Scanner(System.in).nextInt();
            switch (i) {
                case 1 -> {
                    boolean trueOrFalse1 = true;
                    while (trueOrFalse1) {
                        System.out.println("""
                                1 - Save country
                                2 - Save countries
                                3 - Get all
                                4 - Get by ID
                                5 - Delete by ID
                                6 - Delete all
                                7 - Update
                                8 - Get by long description
                                9 - Get quantity by country name
                                0 - EXIT
                                """);
                        int j = new Scanner(System.in).nextInt();
                        switch (j) {
                            case 1 -> {
                                System.out.println("Enter country: ");
                                String country = new Scanner(System.in).nextLine();
                                System.out.println("Enter description: ");
                                String description = new Scanner(System.in).nextLine();
                                System.out.println(countryService.saveCountry(new Country(country, description)));
                            }
                            case 2 -> System.out.println(countryService.saveCountry(countryList));
                            case 3 -> System.out.println(countryService.getAllCountry());
                            case 4 -> {
                                System.out.println("Enter country ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(countryService.getByID(id));
                            }
                            case 5 -> {
                                System.out.println("Enter country ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(countryService.deleteByID(id));
                            }
                            case 6 -> System.out.println(countryService.deleteAll());
                            case 7 -> {
                                System.out.println("Enter old country ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println("Enter new country: ");
                                String country = new Scanner(System.in).nextLine();
                                System.out.println("Enter new description: ");
                                String description = new Scanner(System.in).nextLine();
                                System.out.println(countryService.updateByID(id, new Country(country, description)));
                            }
                            case 8 -> System.out.println(countryService.getByLongDescription());
                            case 9 -> {
                                System.out.println("Enter country: ");
                                String country = new Scanner(System.in).nextLine();
                                System.out.println(countryService.getByCountryNameCount(country));
                            }
                            case 0 -> trueOrFalse1 = false;
                        }
                    }
                }
                case 2 -> {
                    boolean trueOrFalse2 = true;
                    while (trueOrFalse2) {
                        System.out.println("""
                                1 - Save address
                                2 - Save addresses
                                3 - Get all
                                4 - Get by ID
                                5 - Delete by ID
                                6 - Delete all
                                7 - Update by ID
                                0 - EXIT
                                """);
                        int k = new Scanner(System.in).nextInt();
                        switch (k) {
                            case 1 -> {
                                System.out.println("Enter country id: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println("Enter region name: ");
                                String regionName = new Scanner(System.in).nextLine();
                                System.out.println("Enter street: ");
                                String street = new Scanner(System.in).nextLine();
                                System.out.println("Enter home number: ");
                                int number = new Scanner(System.in).nextInt();
                                System.out.println(addressService.saveAddress(id, new Address(regionName, street, number)));
                            }
                            case 2 -> {
                                System.out.println("Enter country id: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(addressService.saveAddress(id, addressList));
                            }
                            case 3 -> System.out.println(addressService.getAll());
                            case 4 -> {
                                System.out.println("Enter  id: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(addressService.getByID(id));
                            }
                            case 5 -> {
                                System.out.println("Enter  id: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(addressService.deleteByID(id));
                            }
                            case 6 -> System.out.println(addressService.deleteAll());
                            case 7 -> {
                                System.out.println("Enter old address id: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println("Enter region name: ");
                                String regionName = new Scanner(System.in).nextLine();
                                System.out.println("Enter street: ");
                                String street = new Scanner(System.in).nextLine();
                                System.out.println("Enter home number: ");
                                int number = new Scanner(System.in).nextInt();
                                System.out.println(addressService.updateByID(id, new Address(regionName, street, number)));
                            }
                            case 0 -> trueOrFalse2 = false;
                        }
                    }
                }
                case 3 -> {
                    boolean trueOrFalse3 = true;
                    while (trueOrFalse3) {
                        System.out.println("""
                                1 - Save programmer
                                2 - Save programmers
                                3 - Get all
                                4 - Get by ID
                                5 - Delete by ID
                                6 - Delete all
                                7 - Update
                                8 - Get programmer by country
                                9 - Get young programmer
                                10 - Get senior programmer
                                11 - Add constrain unique
                                0 - EXIT
                                """);
                        int l = new Scanner(System.in).nextInt();
                        switch (l) {
                            case 11 -> System.out.println(programmerService.addConstrainUnique());
                            case 1 -> {
                                System.out.println("Enter full name: ");
                                String name = new Scanner(System.in).nextLine();
                                System.out.println("Enter email: ");
                                String email = new Scanner(System.in).nextLine();
                                System.out.println("Enter date of birth (yyyy/mm/dd): ");
                                String date = new Scanner(System.in).nextLine();
                                String[] s = date.split("/");
                                LocalDate of = LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                                System.out.println("Enter status(Collaborator,Owner): ");
                                String status = new Scanner(System.in).nextLine();
                                System.out.println("Enter address ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(programmerService.saveProgrammer(new Programmer(name, email, of, Status.valueOf(status.toUpperCase())), id));
                            }
                            case 2 -> {
                                System.out.println("Enter address ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(programmerService.saveProgrammer(programmerList, id));
                            }
                            case 3 -> System.out.println(programmerService.getAll());
                            case 4 -> {
                                System.out.println("Enter ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(programmerService.getByID(id));
                            }
                            case 5 -> {
                                System.out.println("Enter ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(programmerService.deleteByID(id));
                            }
                            case 6 -> System.out.println(programmerService.deleteAll());
                            case 7 -> {
                                System.out.println("Enter old programmer ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println("Enter full name: ");
                                String name = new Scanner(System.in).nextLine();
                                System.out.println("Enter email: ");
                                String email = new Scanner(System.in).nextLine();
                                System.out.println("Enter date of birth (yyyy/mm/dd): ");
                                String date = new Scanner(System.in).nextLine();
                                String[] s = date.split("/");
                                LocalDate of = LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                                System.out.println("Enter status(Collaborator,Owner): ");
                                String status = new Scanner(System.in).nextLine();
                                System.out.println(programmerService.update(id, new Programmer(name, email, of, Status.valueOf(status.toUpperCase()))));
                            }
                            case 8 -> {
                                System.out.println("Enter country: ");
                                String country = new Scanner(System.in).nextLine();
                                System.out.println(programmerService.getProgrammerByCountry(country));
                            }
                            case 9 -> System.out.println(programmerService.getYoungProgrammer());
                            case 10 -> System.out.println(programmerService.getSeniorProgrammer());
                            case 0 -> trueOrFalse3 = false;
                        }
                    }
                }
                case 4 -> {
                    boolean trueOrFalse4 = true;
                    while (trueOrFalse4) {
                        System.out.println("""
                                1 - Save project
                                2 - Save projects
                                3 - Get all
                                4 - Get by ID
                                5 - Delete by ID
                                6 - Delete all
                                7 - Update 
                                8 - Assign programmer
                                9 - Max price project
                                10 - Get quick project
                                0 - EXIT
                                """);
                        int p = new Scanner(System.in).nextInt();
                        switch (p) {
                            case 1 -> {
                                System.out.println("Enter name project: ");
                                String name = new Scanner(System.in).nextLine();
                                System.out.println("Enter description: ");
                                String description = new Scanner(System.in).nextLine();
                                System.out.println("Enter date of start (yyyy/mm/dd): ");
                                String startDate = new Scanner(System.in).nextLine();
                                String[] s = startDate.split("/");
                                LocalDate start = LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                                System.out.println("Enter date of finish (yyyy/mm/dd): ");
                                String finishDate = new Scanner(System.in).nextLine();
                                String[] a = finishDate.split("/");
                                LocalDate finish = LocalDate.of(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
                                System.out.println("Enter price: ");
                                Long price = new Scanner(System.in).nextLong();
                                System.out.println(projectService.saveProject(new Project(name, description, start, finish, BigDecimal.valueOf(price))));
                            }
                            case 2 -> System.out.println(projectService.saveProject(projectList));
                            case 3 -> System.out.println(projectService.getAll());
                            case 4 -> {
                                System.out.println("Enter ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(projectService.getByID(id));
                            }
                            case 5 -> {
                                System.out.println("Enter ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println(projectService.deleteByID(id));
                            }
                            case 6 -> System.out.println(projectService.deleteAll());
                            case 7 -> {
                                System.out.println("Enter old project ID: ");
                                Long id = new Scanner(System.in).nextLong();
                                System.out.println("Enter name project: ");
                                String name = new Scanner(System.in).nextLine();
                                System.out.println("Enter description: ");
                                String description = new Scanner(System.in).nextLine();
                                System.out.println("Enter date of start (yyyy/mm/dd): ");
                                String startDate = new Scanner(System.in).nextLine();
                                String[] s = startDate.split("/");
                                LocalDate start = LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                                System.out.println("Enter date of finish (yyyy/mm/dd): ");
                                String finishDate = new Scanner(System.in).nextLine();
                                String[] a = finishDate.split("/");
                                LocalDate finish = LocalDate.of(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
                                System.out.println("Enter price: ");
                                Long price = new Scanner(System.in).nextLong();
                                System.out.println(projectService.update(id, new Project(name, description, start, finish, BigDecimal.valueOf(price))));
                            }
                            case 8 -> {
                                System.out.println("Enter project ID: ");
                                Long projectID = new Scanner(System.in).nextLong();
                                System.out.println("Enter programmer ID: ");
                                Long programmerId = new Scanner(System.in).nextLong();
                                System.out.println(projectService.assignProgrammer(projectID, programmerId));
                            }
                            case 9 -> System.out.println(projectService.maxPriceProject());
                            case 10 -> System.out.println(projectService.getQuickProject());
                            case 0 -> trueOrFalse4 = false;
                        }
                    }
                }
            }
        }
    }
}
