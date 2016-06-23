package by.epam.shop.validator;

public interface Validator<T> {
	String isValid(T t);
}
