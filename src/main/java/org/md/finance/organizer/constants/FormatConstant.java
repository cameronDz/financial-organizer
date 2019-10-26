package org.md.finance.organizer.constants;

import java.text.DecimalFormat;

public class FormatConstant {
	public static final String BASIC_ACCOUNT_OUTPUT = "%-18s %12s %8s %12s %12s";
	public static final String ACCOUNT_TRANSACTION_OUTPUT = "%5s %-41s %15s %15s";
	public static final String EMPTY_STRING = "";
	public static final String NEW_LINE = "\n";

	public final static DecimalFormat DOLLAR_DECIMAL = new DecimalFormat("$#,###,###.00");
	public final static DecimalFormat PERCENT_DECIMAL = new DecimalFormat("##.000%");
}
