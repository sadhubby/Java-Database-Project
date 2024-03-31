public class Employee{
    static Scanner sc = new Scanner(System.in);

    public static void emp_in() {
        int stopper = 1;
        do {
            System.out.print("Enter Employee ID: ");
            String employee_Number = sc.nextLine();
            System.out.print("Enter Employee Lastname: ");
            String employee_lastName = sc.nextLine();
            System.out.print("Enter Employee Firstname: ");
            String employee_firstName = sc.nextLine();
            System.out.print("Enter Extension: ");
            String employee_extension = sc.nextLine();
            System.out.print("Enter Email: ");
            String employee_email = sc.nextLine();
            System.out.print("Enter Office code: ");
            String employee_officeCode = sc.nextLine();
            System.out.print("Enter reporting to: ");
            String employee_reportsTo = sc.nextLine();
            System.out.print("Enter Job: ");
            String employee_jobTitle = sc.nextLine();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbsales", "admin", "DLSU1234"
                );
                PreparedStatement stmt;
                String query = "INSERT INTO employees " +
                        "(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = con.prepareStatement(query);
                stmt.setString(1, employee_Number);
                stmt.setString(2, employee_lastName);
                stmt.setString(3, employee_firstName);
                stmt.setString(4, employee_extension);
                stmt.setString(5, employee_email);
                stmt.setString(6, employee_officeCode);
                stmt.setString(7, employee_reportsTo);
                stmt.setString(8, employee_jobTitle);

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Employees created successfully.");
                } else {
                    System.out.println("Failed to create employees.");
                }

                stmt.close();
                con.close();
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Database connection error...");
                e.printStackTrace();
            }

            System.out.print("Do you want to create another Employee entry? (Y/N): ");
            char insert_loop = sc.next().charAt(0);
            if (insert_loop == 'n' || insert_loop == 'N') {
                stopper = 0;
            } else {
                sc.nextLine(); //Consumes newline made by next() method.
                System.out.println();
            }
        } while (stopper == 1);
    }

    public static void emp_update() {
        int x = 1;
        do {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbsales", "admin", "DLSU1234"
                );
                System.out.print("Enter employee ID: ");
                String employee_Number = sc.next();

                String query_check = "SELECT * FROM employees WHERE officeNumber = ?";
                PreparedStatement ps_check = con.prepareStatement(query_check);
                ps_check.setString(1, employee_Number);

                ResultSet rs_check = ps_check.executeQuery();

                if (rs_check.next()) {
                    System.out.println("Employee found! Here are the details:");
                    System.out.println("Employee ID: " + rs_check.getString("employeeNumber"));
                    System.out.println("Last Name: " + rs_check.getString("lastName"));
                    System.out.println("First Name: " + rs_check.getString("firstName"));
                    System.out.println("Extension: " + rs_check.getString("extension"));
                    System.out.println("Email: " + rs_check.getString("email"));
                    System.out.println("Office Code: " + rs_check.getString("officeCode"));
                    System.out.println("Reporting to: " + rs_check.getString("reportsTo"));
                    System.out.println("Job Title: " + rs_check.getString("jobTitle"));
                    System.out.println();

                    System.out.print("Do you want to update the current details of this employee? (Y/N): ");
                    char update_choice = sc.next().charAt(0);
                    if (update_choice == 'Y' || update_choice == 'y') {
                        System.out.println("Enter the column you will update (1-8), 0 to skip:");
                        if (sc.hasNextInt()) {
                            int col_choice = sc.nextInt();
                            sc.nextLine(); // consumes the newline.
                            if (col_choice >= 1 && col_choice <= 8) {
                                String col_name = "";
                                switch (col_choice) {
                                    case 1:
                                        col_name = "employeeNumber";
                                        break;
                                    case 2:
                                        col_name = "lastName";
                                        break;
                                    case 3:
                                        col_name = "firstName";
                                        break;
                                    case 4:
                                        col_name = "extension";
                                        break;
                                    case 5:
                                        col_name = "email";
                                        break;
                                    case 6:
                                        col_name = "officeCode";
                                        break;
                                    case 7:
                                        col_name = "reportsTo";
                                        break;
                                    case 8:
                                        col_name = "jobTitle";
                                        break;
                                }
                                if (!col_name.isEmpty()) {
                                    System.out.println("Old Value: " + rs_check.getString(col_name));
                                    System.out.print("Enter new value: ");
                                    String new_value = sc.nextLine();

                                    String update_query = "UPDATE employees SET " + col_name + "= ? WHERE employeeNumber = ?";
                                    PreparedStatement ps_update = con.prepareStatement(update_query);
                                    ps_update.setString(1, new_value);
                                    ps_update.setString(2, employee_Number);

                                    int rows_affected = ps_update.executeUpdate();

                                    if (rows_affected > 0) {
                                        System.out.println("Employee record updated successfully!\n");
                                    } else {
                                        System.out.println("Failed to update employee record...");
                                    }
                                    ps_update.close();
                                } else {
                                    System.out.println("Invalid choice.");
                                }
                            } else {
                                System.out.println("Invalid input... Please enter a number: ");
                                sc.next(); // consumes an invalid input.
                            }
                        }
                    }
                } else {
                    System.out.println("Employee with ID " + employee_Number + " does not exist.");
                }
                ps_check.close();
                rs_check.close();

                System.out.print("Do you want to update another employee record? (Y/N) ");
                char continue_choice = sc.next().charAt(0);
                if (continue_choice == 'N' || continue_choice == 'n') {
                    x = 0;
                }
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Database connection error.");
                e.printStackTrace();
            }
        } while (x == 1);
    }

    public static void emp_delete() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/dbsales", "admin", "DLSU1234"
            );

            int x = 1;
            do { 
            System.out.print("Enter the employee ID to delete: ");
            String employeeNumber = sc.next();

            String deleteQuery = "DELETE FROM employees WHERE employeeNumber = ?";
            PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
            deleteStatement.setString(1, employeeNumber);

            try {
                int rowsAffected = deleteStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Record with employee ID " + employeeNumber + " deleted successfully");
                } else {
                    System.out.println("No record found with employee ID " + employeeNumber);
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == 1451) {
                    System.out.println("Error: Cannot delete this record because it is being referenced by other records");
                } else {
                    System.out.println("SQL Error: " + e.getMessage());
                }
            }

            System.out.print("\nDo you want to delete another record? (Y/N): ");
            char deleteLoop = sc.next().charAt(0);
            if (deleteLoop == 'n' || deleteLoop == 'N') {
                x = 0;
            }
        } while (x == 1);

        con.close();
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public static void emp_viewsalesrep() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales", "admin", "DLSU1234"
        );

        System.out.print("Enter the employee ID to view: ");
        String employeeNumber = sc.next();

        String employeesQuery = "SELECT salesRepEmployeeNumber FROM customers WHERE employeeNumber = ? ";
        PreparedStatement employeesPs = con.prepareStatement(employeesQuery);
        employeesPs.setString(1, employeeNumber);

        ResultSet employeesRs = employeesPs.executeQuery();

        System.out.println("Employee assigned to list of customers with " + employeeNumber + ":");
        while (employeesRs.next()) {
            System.out.println("Employee Number: " + employeesRs.getString("salesRepEmployeeNumber"));
        }

        employeesRs.close();
        employeesPs.close();
        con.close();
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
  }
}