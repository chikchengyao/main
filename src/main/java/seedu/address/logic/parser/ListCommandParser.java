package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /** Used to get the "due before" filter option */
    public static final Prefix PREFIX_DUE_BEFORE = new Prefix("b/");
    public static final String DUE_TODAY_OPTION = "today";
    public static final String DUE_END_OF_WEEK_OPTION = "week";
    public static final String DUE_END_OF_MONTH_OPTION = "month";

    /**
     * Parses a string that represents a list filter option.
     *
     * @param s the optional string the denotes the list filter type
     * @return an Optional ListFilter, which is not present if there is no string parameter
     * @throws ParseException if a list filter is present but the filter type is invalid
     */
    private Optional<ListCommand.ListFilter> parseListFilter(Optional<String> s)throws ParseException {
        if (s.isPresent()) {
            String val = s.get();
            switch (val) {
            case DUE_TODAY_OPTION:
                return Optional.of(ListCommand.ListFilter.DUE_TODAY);
            case DUE_END_OF_WEEK_OPTION:
                return Optional.of(ListCommand.ListFilter.DUE_END_OF_WEEK);
            case DUE_END_OF_MONTH_OPTION:
                return Optional.of(ListCommand.ListFilter.DUE_END_OF_MONTH);
            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DUE_BEFORE);

        Optional<ListCommand.ListFilter> listFilter = parseListFilter(argMultimap.getValue(PREFIX_DUE_BEFORE));

        return listFilter
                .flatMap(filter -> Optional.of(new ListCommand(filter)))
                .orElseGet(() -> new ListCommand());
    }
}
