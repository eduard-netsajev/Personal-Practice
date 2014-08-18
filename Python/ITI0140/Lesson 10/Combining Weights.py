__author__ = 'Netšajev'


def find_sums(weights, min_sum, max_sum):
    """
    Assume you have a set of weights w1...wn and you
    would like to find a subset of them that gets as
    close as possible to a certain total weight. For
    n weights, there are 2 ^ n possible subsets, and
    there is no known algorithm that would solve this
    problem efficiently.

    Write a function findSums(weights,minSum,maxSum) that takes three arguments:
    :weights - a list of weights
    :minSum - a minimum total weight
    :maxSum - a maximum total weight
    The function should return a list of all subsets of the weights for which the sum falls
    into the allowed range.
    E.g. a call
    > findSums([1,2,4,8],10,13)
    should return
    [[2,8],[1,2,8],[4,8],[1,4,8]]
    (or a permutation thereof)
    """
    return list(filter(lambda x: min_sum <= sum(x) <= max_sum, [[item for item, flag in zip(weights, binum) if flag == '1'] for binum in [bin(item)[3:] for item in range(2 ** len(weights), 2 ** len(weights+[0]))]]))

weights = [1, 2, 4, 8]
print("Weights:", weights, "\nVariations:", find_sums(weights,10,13))