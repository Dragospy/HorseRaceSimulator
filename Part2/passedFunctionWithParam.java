package Part2;

//Used to pass functions as parameters, whilst also allowing those functions to have a parameter
public interface passedFunctionWithParam<T> {
    void execute(T value);
}
