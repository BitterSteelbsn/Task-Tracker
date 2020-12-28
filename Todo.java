import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Todo {

    int completedTasks, pendingTasks;

    public Todo() {
        pendingTasks  = getTasks().size();
        completedTasks = getCompletedTaskList().size();
    }
    

    public void add(String newTask) {
        ArrayList<String> taskList = getTasks();
        taskList.add(0, newTask);
        updateTasks(taskList);
    }

    public void ls() {
        ArrayList<String> taskList = getTasks();

        if (taskList.size() == 0) {
            System.out.println("There are no pending todos!");
        } else {
            int counter = taskList.size();
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println(String.format("[%d] %s", counter, taskList.get(i)));
                counter--;
            }
        }        
    }

    public boolean del(int n) {
        ArrayList<String> taskList = getTasks();

        if (n > taskList.size()) {
            return false;
        }
        int to_del_task = taskList.size() - n;
        taskList.remove(to_del_task);
        updateTasks(taskList);
        return true;
    }

    public boolean done(int n) {
        ArrayList<String> taskList = getTasks();

        if (n > taskList.size()) {
            return false;
        }

        int to_done_task = taskList.size() - n;
        String completedTask = taskList.remove(to_done_task);
        updateTasks(taskList);
        updateDoneTasks(completedTask);

        return true;
    }

    private ArrayList<String> getCompletedTaskList() {
        ArrayList<String> taskList = new ArrayList<>();
        try {
            String filePath = System.getProperty("user.dir") + "\\done.txt";
            //File file = new File("done.txt");
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader br = null;
            try {
                String st;
                //br= new BufferedReader(new FileReader("done.txt"));
                br= new BufferedReader(new FileReader(file));
                while((st = br.readLine()) != null) {
                    //System.out.println(st);
                    taskList.add(st);
                }
            } catch (IOException e) { 
                e.printStackTrace(); 
            }
            finally {
                
                try {
                    if (br!= null)
                        br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskList;
    }

    private void updateDoneTasks(String completedTask) {
        LocalDate ldObj = LocalDate.now();
        String to_write = String.format("x %s %s", ldObj, completedTask);

       // ArrayList<String> taskList = getCompletedTaskList();
        try {
            String filePath = System.getProperty("user.dir") + "\\done.txt";
            File file = new File(filePath);
            //File file = new File("done.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw  = new BufferedWriter(new FileWriter(file, true));
            try {
                // for (int i = 0; i < taskList.size(); i++) {
                //     bw.write(taskList.get(i));
                //     bw.newLine();
                // }
                
                bw.write(to_write);
                bw.newLine();
                bw.flush();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (bw!= null)
                        bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void help() {
        System.out.println("Usage :-");
        System.out.println("$ ./todo add \"todo item\"  # Add a new todo");
        System.out.println("$ ./todo ls               # Show remaining todos");
        System.out.println("$ ./todo del NUMBER       # Delete a todo");
        System.out.println("$ ./todo done NUMBER      # Complete a todo");
        System.out.println("$ ./todo help             # Show usage");
        System.out.println("$ ./todo report           # Statistics");
    }

    public void report() {
        //LocalDate ldObj = LocalDate.now();
        System.out.println(String.format("%s Pending : %d Completed : %d", LocalDate.now(), pendingTasks, completedTasks));
    }

    private ArrayList<String> getTasks() {
        ArrayList<String> taskList = new ArrayList<>();
        try {
            //File file = new File("todo.txt");
            String filePath = System.getProperty("user.dir") + "\\todo.txt";
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader br = null;
            try {
                String st;
                br= new BufferedReader(new FileReader(file));
                while((st = br.readLine()) != null) {
                    //System.out.println(st);
                    taskList.add(st);
                }
            } catch (IOException e) { 
                e.printStackTrace(); 
            }
            finally {
                
                try {
                    if (br!= null)
                        br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskList;
    }

    private void updateTasks(ArrayList<String> taskList) {
        try {
            //File file = new File("todo.txt");
            String filePath = System.getProperty("user.dir") + "\\todo.txt";
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw  = new BufferedWriter(new FileWriter(file));
            try {
                for (int i = 0; i < taskList.size(); i++) {
                    bw.write(taskList.get(i));
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                    
                try {
                    if (bw!= null)
                        bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	public static void main(String args[]) throws Exception{

        Todo todoList = new Todo();

        if (args.length == 0) {
            todoList.help();
            System.exit(1);
        }

		switch (args[0]) {
            case "add": {
                if (args.length != 2) {
                    System.out.println("Error: Missing todo string. Nothing added!");
                } else {
                    todoList.add(args[1]);
                    System.out.println(String.format("Added todo: \"%s\"", args[1]));
                }   
            } break;
            case "ls": {
                todoList.ls();
            } break;
            case "del": {
                if (args.length != 2) {
                    System.out.println("Error: Missing NUMBER for deleting todo.");
                } else {
                    if (todoList.del(Integer.parseInt(args[1]))) {
                        System.out.println(String.format("Deleted todo #%s", args[1]));
                    } else {
                        System.out.println(String.format("Error: todo #%s does not exist. Nothing deleted.", args[1]));
                    }
                }                
            } break;
            case "done": {
                if (args.length != 2) {
                    System.out.println("Error: Missing NUMBER for marking todo as done.");
                } else {
                    if (todoList.done(Integer.parseInt(args[1]))) {
                        System.out.println(String.format("Marked todo #%s as done.", args[1]));
                    } else {
                        System.out.println(String.format("Error: todo #%s does not exist.", args[1]));
                    }
                }
            } break;
            case "help": {
                todoList.help();
            } break;
            case "report": {
                todoList.report();
            } break;
            default:
            break;
        }
	}

    
}
