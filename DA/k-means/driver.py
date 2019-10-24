import pandas as pd
import math
import matplotlib.pyplot as plt
import random
import os


path_to_file = "E:\\LP1\\DA\\k-means\\dataset\\iris.csv"

data = pd.read_csv(path_to_file, encoding = 'utf-8')

print("Describing the columns")

cols = data.columns

print(cols)
print(len(cols))

full_data = []

for i in range(1,6):
    feature = cols[i]
    Series_feature = ((data[feature]))   
    full_data.insert(0, Series_feature)
    count = len(Series_feature)
    print("count =", count)
    type_of_data = type(Series_feature[2])
    if Series_feature.dtype.name == 'float64' or Series_feature.dtype.name == 'int64':
        print("Attribute is numeric")
    if Series_feature.dtype.name == type('Saurabh'):
        print("Attribute is nominal")

    mean = 0
    if i != 5:
        for element in Series_feature:
            mean += element
        print("Summation", mean) 
        print("mean", mean/ count)
        mean = mean/count
        x_x2_sum = 0
        for element in Series_feature:
            x_x2_sum += (element - mean)**2
        var = (x_x2_sum)/(count - 1)
        std_dev = math.sqrt(var)
        print("Standard deviation", std_dev)

        #finding the minimum feature
        min = 5
        for element in Series_feature:
            if element < min:
                min = element
        print("Minimum value of feature is", min)

        #finding the max value of feature
        max = -1
        for element in Series_feature:
            if element > max:
                max = element
        print("Maximum value of the feature is", max)

        Series_feature = Series_feature.sort_values(ascending=True)

        k = 0.25

        position = (k / 100) * count

        percentile = 0

        if position.is_integer():
            percentile = (Series_feature[position] + Series_feature[position + 1]) / 2
        else:
            position = int(position)
            percentile = Series_feature[position]
        print(k, "th percetile of the feature", percentile)
        print("=========================================================")


# data visualization in histogram
for i in range(1, 5):
    feature = cols[i]
    data[feature].plot(kind="Hist", bins=150)
    plt.title(feature+" distribution")
    plt.xlabel(feature)
    plt.show()

print("i am here now")


# removing the id column
new_data = data[["SepalLengthCm", "SepalWidthCm", "PetalLengthCm", "PetalWidthCm"]]
fig = plt.figure(1, figsize = (10, 7))
new_data.boxplot()
plt.show()
    