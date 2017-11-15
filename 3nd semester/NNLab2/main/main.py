from sklearn import datasets
from sklearn import svm

def printImage(a):
    for index in range(0, 8):
        i = index * 8
        print("%-3d %-3d %-3d %-3d %-3d %-3d %-3d %-3d " % (a[i], a[i+1], a[i+2], a[i+3], a[i+4], a[i+5], a[i+6], a[i+7]))

digits = datasets.load_digits()
# print(len(digits.data))
# print(digits)
# for subarray in digits.data:
#     print(len(subarray))

# print(len(digits.target))
# print
n = len(digits.data)
tries = 10

clf = svm.SVC(gamma=0.001, C=100.)
clf.fit(digits.data[:-tries], digits.target[:-tries])
for i in range(1, tries + 1):
    data = digits.data[n - i]
    correctAnswer = digits.target[n - i]
    result = clf.predict(digits.data[(n - i):(n - i + 1)])
    print("Data:")
    printImage(data)
    print("Correct answer:", correctAnswer)
    print("Result:", result)
    print()
