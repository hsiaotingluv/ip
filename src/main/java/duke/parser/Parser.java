package duke;

import duke.DeadlineFormatException;
import duke.EmptyDescriptionException;
import duke.EventFormatException;
import duke.OutOfBoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a parser that interprets the user input.
 *
 * @author Chen Hsiao Ting
 * @version CS2103T AY21/22 Semester 1
 */
public class Parser {
    private String input;
    private String[] inputCommandAndDescription;

    /**
     * A constructor for Parser.
     *
     * @param input User input.
     */
    public Parser(String input) {
        this.input = input;
        this.inputCommandAndDescription = input.split(" ", 2);
    }

    /**
     * Returns the command word.
     *
     * @return String of the first word.
     */
    public String getCommandWord() {
        return inputCommandAndDescription[0];
    }

    /**
     * Returns an integer of the index.
     *
     * @param size Size of current TaskList.
     * @return Integer of index.
     * @throws OutOfBoundException If index is less than 1 or more than size of TaskList.
     */
    public int getIndex(int size) throws OutOfBoundException {
        int index = Integer.parseInt(input.split(" ")[1]);
        System.out.println("GET INDEX FUNCTION");
        if (index > size || index < 1) {
            throw new OutOfBoundException(size);
        }
        return index - 1;
    }

    /**
     * Returns description of the to-do task.
     *
     * @return String description of the to-do task.
     * @throws EmptyDescriptionException If user input an empty description.
     */
    public String getTodoDescription() throws EmptyDescriptionException {
        if (inputCommandAndDescription.length == 1) {
            throw new EmptyDescriptionException();
        }
        return inputCommandAndDescription[1];
    }

    /**
     * Returns description of the deadline task.
     *
     * @return String description of the deadline task.
     * @throws EmptyDescriptionException If user input an empty description.
     * @throws DeadlineFormatException   If user input an invalid deadline format.
     */
    public String getDeadlineDescription() throws EmptyDescriptionException, DeadlineFormatException {
        if (inputCommandAndDescription.length == 1) {
            throw new EmptyDescriptionException();
        } else if (!inputCommandAndDescription[1].contains("/by ")) {
            throw new DeadlineFormatException();
        }
        String deadlineDescription = inputCommandAndDescription[1];
        return deadlineDescription.split("/by ", 2)[0].trim();
    }

    /**
     * Returns the date of a deadline task.
     *
     * @return String of the deadline task due date.
     * @throws DeadlineFormatException If user input an invalid deadline format.
     */
    public String getDeadlineDate() throws DeadlineFormatException {
        String deadlineDescription = inputCommandAndDescription[1];
        String date = deadlineDescription.split("/by ", 2)[1].trim();
        if (!isValidDateTime(date)) {
            throw new DeadlineFormatException();
        }
        return date;
    }

    /**
     * Returns description of the event task.
     *
     * @return String description of the event task.
     * @throws EmptyDescriptionException If user input an empty description.
     * @throws EventFormatException      If user input an invalid event format.
     */
    public String getEventDescription() throws EmptyDescriptionException, EventFormatException {
        if (inputCommandAndDescription.length == 1) {
            throw new EmptyDescriptionException();
        } else if (!inputCommandAndDescription[1].contains("/from ")
                || !inputCommandAndDescription[1].contains("/to ")) {
            throw new EventFormatException();
        }
        String eventDescription = inputCommandAndDescription[1];
        return eventDescription.split("/from ", 2)[0].trim();
    }

    /**
     * Returns the start and end dates of an event task.
     *
     * @return String of the start and end dates.
     * @throws DeadlineFormatException If user input an invalid event format.
     */
    public String getEventDate() throws EventFormatException {
        String eventDescription = inputCommandAndDescription[1];
        String[] textAndDuration = eventDescription.split("/from ", 2);
        String text = textAndDuration[0].trim();
        String duration = textAndDuration[1].trim();

        // split start date and end date
        String[] startAndEnd = duration.split("/to ", 2);
        String startDate = startAndEnd[0].trim();
        String endDate = startAndEnd[1].trim();

        if (!isValidDateTime(startDate) || !isValidDateTime(endDate)) {
            throw new EventFormatException();
        }
        return startDate + " " + endDate;
    }

    /**
     * Returns string of keyword input by user.
     *
     * @return String representation of keyword.
     */
    public String getKeyword() {
        return inputCommandAndDescription[1];
    }

    /**
     * Returns true if user enter a valid DateTime format, false if invalid.
     *
     * @param date User input date.
     * @return Boolean value that represents either valid or invalid date.
     */
    public boolean isValidDateTime(String date) {
        Boolean isValid = true;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            date = date.replace("T", " ");
            LocalDateTime dateTime = LocalDateTime.from(formatter.parse(date));
        } catch (DateTimeParseException e) {
            isValid = false;
            System.out.println(isValid);
        }
        return isValid;
    }
}
