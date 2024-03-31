import java.sql.*;
import java.util.Scanner;

public class App_Methods {
    static Scanner sc = new Scanner(System.in);

    public static void product_insert(){
        int stopper = 1;
        do{
        System.out.print("Enter product id: ");
        String product_code = sc.nextLine();
        System.out.print("Enter product name: ");
        String product_name = sc.nextLine();
        System.out.print("Enter product line: ");
        String product_line = sc.nextLine();
        System.out.print("Enter product scale: ");
        String product_scale = sc.nextLine();
        System.out.print("Enter product vendor: ");
        String product_vendor = sc.nextLine();
        System.out.print("Enter product description: ");
        String product_desc = sc.nextLine();
        System.out.print("Enter product quantity in stock: ");
        int product_stock = sc.nextInt();
        System.out.print("Enter product price: ");
        double product_price = sc.nextDouble();
        System.out.print("Enter product MSRP: ");
        double product_MSRP = sc.nextDouble();


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
         PreparedStatement stmt;
         String q1 = "INSERT INTO products" + "(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP)"
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
         stmt = con.prepareStatement(q1);
         stmt.setString(1, product_code);
         stmt.setString(2, product_name);
         stmt.setString(3, product_line);
         stmt.setString(4, product_scale);
         stmt.setString(5, product_vendor);
         stmt.setString(6, product_desc);
         stmt.setInt(7, product_stock);
         stmt.setDouble(8, product_price);
         stmt.setDouble(9, product_MSRP);

         int x = stmt.executeUpdate();
         if(x > 0){
            System.out.println("\nSuccessfully inserted\n");
         }
         else{
            System.out.println("Not inserted\n");
         }
         con.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        //    System.out.println("\nError. New record not inserted. productLine is referenced");

        }

        System.out.print("Insert another record (Y/N): ");
        char insert_loop = sc.next().charAt(0);

        if(insert_loop == 'n' || insert_loop == 'N'){
            stopper = 0;
        }
        else{
            System.out.println();
        }

    }while(stopper == 1);

}   // end of Product Insert

    public static void product_update(){
        
   
        ResultSet rs_for_query = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/dbsales","root","12345");

        

        String query_test = "SELECT * FROM products WHERE productCode = ?";
        PreparedStatement ps_stmt = con.prepareStatement(query_test);
       
        System.out.print("Enter product code: ");
        String product_code = sc.next();

        ps_stmt.setString(1, product_code);
        rs_for_query = ps_stmt.executeQuery();
        boolean isTrue = true;

            //if statement for found record
            if(true == rs_for_query.next()){
          
                //rs_for_query = ps_stmt.executeQuery();
                do{
                    String get_code = rs_for_query.getString("productCode");
                    String get_name = rs_for_query.getString("productName");
                    String get_line = rs_for_query.getString("productLine");
                    String get_scale = rs_for_query.getString("productScale");
                    String get_vendor = rs_for_query.getString("productVendor");
                    String get_desc = rs_for_query.getString("productDescription");
                    int get_quantity = rs_for_query.getInt("quantityInStock");
                    Double get_buyPrice = rs_for_query.getDouble("buyPrice");
                    Double get_MSRP = rs_for_query.getDouble("MSRP");
                   
                    System.out.println("\nproductCode:  " + get_code);
                    System.out.println("productName:  " + get_name);
                    System.out.println("productLine:  " + get_line);
                    System.out.println("productScale:  "+ get_scale);
                    System.out.println("productVendor: "+ get_vendor);
                    System.out.println("productDescription:  "+ get_desc);
                    System.out.println("quantityInStock:  "+ get_quantity);
                    System.out.println("buyPrice:  "+ get_buyPrice);
                    System.out.println("MSRP:  "+ get_MSRP);
                } while(rs_for_query.next()); 

                System.out.print("\nWant to update this record? (Y/N): ");
                char y_or_n = sc.next().charAt(0);
                if(y_or_n == 'y' || y_or_n == 'Y'){
                    //do update here
                    int update_stopper = 1;
                    do{
                        System.out.print("Enter column name to update: ");
                        String col_name = sc.next();
                        System.out.print("Enter updated value: ");
                        String new_value = sc.next();
                 
                        String update_query = "UPDATE products SET " + col_name + " = ? " + "WHERE productCode=?";
                        PreparedStatement ps_for_update = con.prepareStatement(update_query);
                        ps_for_update.setString(1, new_value);     
                        ps_for_update.setString(2, product_code);

                        int x = ps_for_update.executeUpdate(); 
                        if(x == 1){
                            System.out.println("\nRecord updated\n");
                            System.out.print("Do you still want to keep updating the record "+ product_code +" (Y/N): ");
                            char loop_update_yOrN = sc.next().charAt(0);

                            if(loop_update_yOrN == 'y' || loop_update_yOrN == 'Y'){
                                System.out.println("Continuing...\n");
                                String loop_query = "SELECT * FROM products WHERE productCode = ?";
                                PreparedStatement ps_loop = con.prepareStatement(loop_query);
                                ps_loop.setString(1, product_code);
                                ResultSet loop_output = ps_loop.executeQuery();

                                while(loop_output.next()){
                                    String get_code_loop = loop_output.getString("productCode");
                                    String get_name_loop = loop_output.getString("productName");
                                    String get_line_loop = loop_output.getString("productLine");
                                    String get_scale_loop = loop_output.getString("productScale");
                                    String get_vendor_loop = loop_output.getString("productVendor");
                                    String get_desc_loop = loop_output.getString("productDescription");
                                    int get_quantity_loop = loop_output.getInt("quantityInStock");
                                    Double get_buyPrice_loop = loop_output.getDouble("buyPrice");
                                    Double get_MSRP_loop = loop_output.getDouble("MSRP");
                                   
                                    System.out.println("productCode:  " + get_code_loop);
                                    System.out.println("productName:  " + get_name_loop);
                                    System.out.println("productLine:  " + get_line_loop);
                                    System.out.println("productScale:  "+ get_scale_loop);
                                    System.out.println("productVendor: "+ get_vendor_loop);
                                    System.out.println("productDescription:  "+ get_desc_loop);
                                    System.out.println("quantityInStock:  "+ get_quantity_loop);
                                    System.out.println("buyPrice:  "+ get_buyPrice_loop);
                                    System.out.println("MSRP:  "+ get_MSRP_loop);
                                } 
                                System.out.println();
                            }
                            else{
                                System.out.println();
                               update_stopper = 0;
                            }
                        }
                        else{
                            System.out.println("Error updating\n");
                        }
                    }while(update_stopper == 1); 
                }
                else{
                    System.out.println("\nNot updating...\n");
                }
            } else {    // produces the entire record
                System.out.println("\nRecord does not exist. Going back to main menu...\n");
            }
         con.close();
        }catch(Exception e){System.out.println(e);}
            }

    public static void product_delete(){
        int stopper = 1;
        do{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
            
            System.out.print("Enter column to search for value of deletion: ");
            String col_name = sc.next();
   
            System.out.print("Enter value to delete: ");
            String value = sc.next();

            String delete_query = "DELETE FROM products WHERE " + col_name + "= ?";
            PreparedStatement ps_delete = con.prepareStatement(delete_query);

            ps_delete.setString(1, value);

            int rows_affected = ps_delete.executeUpdate();

            if(rows_affected > 0){
                System.out.println("Record " + value +" deleted successfully\n");
            } else{
                System.out.println("Record does not exist or cannot be deleted\n");
            }

            System.out.println("Do you want to delete another record?(Y/N):  ");
            char delete_loop = sc.next().charAt(0);

            if(delete_loop == 'n' || delete_loop == 'N'){
                stopper = 0;
            }
            con.close();

    } catch(SQLException e){

            if(e.getErrorCode() == 1451){
                System.out.println("Error: Cannot delete record because it is being used as a reference by other records");
            }


    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    }while(stopper == 1);
}
     
    public static void product_view_orderlist(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
                );


            System.out.print("Enter product code to view: ");
            String view_code = sc.next();
           

            String view_record = "SELECT * FROM products WHERE productCode = ?";
            PreparedStatement view_record_ps = con.prepareStatement(view_record);
            view_record_ps.setString(1, view_code);

            ResultSet record_rs = view_record_ps.executeQuery();

            while(record_rs.next()){
                System.out.println("\nProduct code: " + record_rs.getString("productCode"));
                System.out.println("Product name: " + record_rs.getString("productName"));
                System.out.println("Product line: " + record_rs.getString("productLine"));
                System.out.println("Product scale: " + record_rs.getString("productScale"));
                System.out.println("Product vendor: " + record_rs.getString("productVendor"));
                System.out.println("Product desc: " + record_rs.getString("productDescription"));
                System.out.println("Product stock: " + record_rs.getString("quantityInStock"));
                System.out.println("Product price: " + record_rs.getString("buyPrice"));
                System.out.println("Product MSRP: " + record_rs.getString("MSRP"));
                System.out.println();
            }

            System.out.print("Enter year to view: ");
            String view_year = sc.next();

            String view_record_given_year = "SELECT od.orderNumber, od.quantityOrdered, od.orderLineNumber, o.orderDate, o.customerNumber FROM products p RIGHT JOIN orderdetails od ON p.productCode = od.productCode "
                                            + "RIGHT JOIN orders o ON od.orderNumber = o.orderNumber "
                                            + "WHERE p.productCode = " + "'" + view_code +"'"
                                            + "AND YEAR(orderDate) = " + view_year;

            PreparedStatement view_record_year = con.prepareStatement(view_record_given_year);

            //view_record_year.setString(1, view_code);

            ResultSet view_given_year_rs = view_record_year.executeQuery();

            if(view_given_year_rs.next()){
            while (view_given_year_rs.next()) {
                
                String orderNumber = view_given_year_rs.getString("od.orderNumber");
                String quantityOrder = view_given_year_rs.getString("od.quantityOrdered");
                String orderLineNumber = view_given_year_rs.getString("od.orderLineNumber");
                String orderDate = view_given_year_rs.getString("o.orderDate");
                String customerNumber = view_given_year_rs.getString("o.customerNumber");

                System.out.println("\nCustomer: " + customerNumber);
                System.out.println("Order Number: "+ orderNumber);
                System.out.println("Quantity Ordered" + quantityOrder);
                System.out.println("Order Line Number: " + orderLineNumber);
                System.out.println("Date Order made: " + orderDate + "\n");
                
                System.out.println("List generated... going back to main menu\n");
            }
        }
            else{
                System.out.println("No orders made during this year with this product\n");
            }   
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void order_create(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
            con.setAutoCommit(false);
            // find customer code to make order
            // loop order
            // order should be product code
            // if else at end of loop
                //complete transaction?
                    // yes = con.commit
                    // no = con.rollback
                 
            int quantity;

            System.out.print("Enter customer number: ");
            int customer_number_input = sc.nextInt();

            String if_customer_exists = "SELECT * FROM orders WHERE customerNumber = " + customer_number_input;
            
            PreparedStatement ps_if_customer_exists = con.prepareStatement(if_customer_exists);

            ResultSet if_customer_exists_rs = ps_if_customer_exists.executeQuery();

            if (if_customer_exists_rs.next()){
                System.out.println("Customer found\n");

               String customer_code = if_customer_exists_rs.getString("customerNumber");

                System.out.print("Enter product code to buy: ");

                    String product_code= sc.next();

                    String if_productCode_exists = "SELECT * FROM products WHERE productCode = '" + product_code + "'";
                    PreparedStatement if_productCode_ps = con.prepareStatement(if_productCode_exists);
                    ResultSet productCode_rs = if_productCode_ps.executeQuery();

                    if(productCode_rs.next()){
                        System.out.println("Found the product");
                        String find_name = "SELECT productName FROM products WHERE productCode = '" + product_code + "'";
                        PreparedStatement find_name_ps = con.prepareStatement(find_name);
                        ResultSet find_name_rs = find_name_ps.executeQuery();
                        while(find_name_rs.next()){
                            String name = find_name_rs.getString("productName");
                            System.out.println("Product name: "+ name);
                        }

                        System.out.println("Do you want to buy this product: ");
                        char y_or_n = sc.next().charAt(0);
                        if(y_or_n == 'y' || y_or_n == 'Y'){

                            System.out.println("How many: ");
                            quantity = sc.nextInt();

                            String base_quantity = "SELECT quantityInStock WHERE productCode = " + product_code;
                            PreparedStatement base_quantity_ps = con.prepareStatement(base_quantity);
                            ResultSet base_quantity_rs = base_quantity_ps.executeQuery();
                            int get_base_quantity = base_quantity_rs.getInt("quantityInStock");

                            String update_quantity = "UPDATE products SET quantityInStock = quantityInStock - " +quantity +" WHERE productCode = '" +product_code +"'"; 
                            PreparedStatement update_ps = con.prepareStatement(update_quantity);

                            int row_updated = update_ps.executeUpdate();
                   
                            if(row_updated > 0 && ((get_base_quantity - quantity) < get_base_quantity)){
                                System.out.println("You have ordered " +quantity+ "of this product");
                            }
                            else{
                                System.out.println("Not enough quantity in stock for order");
                            }
                        }
                    }
                    else{
                        System.out.println("Product does not exist");
                    }
            } else{
                System.out.println("Customer does not exist\n");
            }
        } catch(SQLException e){System.err.println("SQL Exception:");
        System.err.println("Message: " + e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
        System.err.println("Stack Trace:");e.printStackTrace();
} catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
        {// shows if db is connected or not
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );         
        if(con == null){
            System.out.println("Not Connected");
        }
        else{
            System.out.println("Connected");
        }   
        con.close();
        } catch(Exception e){System.out.println(e);}
        }
}

