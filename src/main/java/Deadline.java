/**
 * Deadline class for tasks that need to be done before a specific date/time
 *
 * @author: Chen Hsiao Ting
 */

public class Deadline extends Task {
    protected String description;
    protected String deadline;

    public Deadline(String description, Boolean isDone) {
        super(description, isDone, "D");
        this.description = description;
    }


    /**
     * Print the status and description of the deadline task.
     * @return a string representation of the deadline task.
     */
    public String getTask() {
        String[] splitted = description.split("/by ", 2);
        String text = splitted[0];
        deadline = splitted[1];
        return "[D]" + "[" + super.getStatusIcon() + "] " + text + "(by: " + deadline + ")";
    }

}
