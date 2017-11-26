from sklearn.neural_network import MLPClassifier
import csv
import numpy as np


def predict_and_print_accuracy(clf, data, canonic_results, label="***"):
    predictedResultList = clf.predict(data)

    concurrences = 0
    for a, b in zip(predictedResultList, canonic_results):
        if abs(a - b) < 0.001:
            concurrences += 1

    print(f"Accuracy ({label}) = ", concurrences / len(canonic_results))


def get_train_data():
    with open("train.csv") as file:
        csv_reader = csv.reader(file, delimiter=',')
        data = np.array(list(csv_reader)[1:])
        data = data[:, 1:]

    result_list_str = data[:, 0]
    data_list_str = data[:, 1:]

    result_values = []
    for val in result_list_str:
        if val not in result_values:
            result_values.append(val)

    result_list = [result_values.index(val) for val in result_list_str]
    data_list = [[float(val) for val in list] for list in data_list_str]

    return result_list, get_normalized_data(data_list)


def get_normalized_data(data_list):
    data_list = np.array(data_list)
    for i in range(0, len(data_list[0])):
        column = data_list[:, i]
        min_val = min(column)
        max_val = max(column)
        divisor = max_val - min_val
        data_list[:, i] = [(val - float(min_val)) / divisor if divisor else 0. for val in column]
    return data_list


def main():
    result_list, data_list = get_train_data()
    print("Data retrieved")

    threshold = 750
    train_list = data_list[:threshold]
    train_result_list = result_list[:threshold]

    test_list = data_list[threshold:]
    test_result_list = result_list[threshold:]

    # lbfgs, sgd, adam
    clf = MLPClassifier(
        solver='sgd',
        max_iter=5000,
        alpha=0.2,
        hidden_layer_sizes=(120, 50),
        tol=1e-6,
        activation='relu',
        learning_rate_init=0.1,
        learning_rate='adaptive',
        random_state=333
    )
    print("MLPClassifier created")

    clf.fit(train_list, train_result_list)
    print("Data fit")

    predict_and_print_accuracy(clf, train_list, train_result_list, "train")
    predict_and_print_accuracy(clf, test_list, test_result_list, "test")


main()
