package gui.posDialog;

import exceptions.BookingException;
import exceptions.OutOfStockException;

@FunctionalInterface
public interface POSDialogCheckoutHandler {
	void handleCheckout() throws OutOfStockException, BookingException;
}
