__author__ = 'Netšajev'

import Webreader as WR


def open_file(filename):
    """Open file, check for errors"""
    f = open(filename)
    # BUGBUG: error check
    return f


def process_file(opened_file):
    """Remove non-alphanumeric & set lowercase"""
    result = ""
    for line in opened_file:
        line = line.lower()
        for letter in line:
            if letter.isalpha() or letter == " " or letter == "\n":
                result += letter
    return result


def count_frequency(processed_file):
    """
    Counting letters in the text

    processed_file: input text
    return: dict of pairs "letter" : count
    """
    result = {}
    for letter in processed_file:
        if letter.isalpha():
            if letter in result.keys():
                result[letter] += 1
            else:
                result[letter] = 1
    return result


def print_result(freq):
    """
    Prints results of count_frequency()
    """
    for letter in sorted(freq.keys(), key=freq.get, reverse=True):
        print("%s: %d" % (letter, freq[letter]))


def count_words(processed_file):
    """
    Count frequency of each unique word in the text

    processed_file: input text with separated words
    return: dict of words and a number of how often they are in the text
    """
    result = {}
    words = processed_file.split()
    for word in words:
        if word in result.keys():
            result[word] += 1
        else:
            result[word] = 1
    return result


def print_words(freq):
    """
    Prints top 10 most frequent words in the text
    freq: dict, result of count_words
    """
    sorted_list = [x for x in freq.items()]
    sorted_list.sort(key=lambda x: str(x[0]))  # sort by key
    sorted_list.sort(key=lambda x: x[1], reverse=True)  # sort by value

    count = len(sorted_list)
    if count > 10:
        count = 10
    for i in range(count):
        print("{}: {}".format(sorted_list[i][0], sorted_list[i][1]))


def analyze(target):
    """
    Analyzes given file or url

    :param target: String of filename or URL
    :return: Dictionary of words and their popularity in the target text
    """
    source = ""
    if target[0:7].lower() == "http://":
        """
        Using webreader for reading files over HTTP.
        """
        ru = WR.read_url(target)
        if not ru:
            print("FAILED!")
        else:
            source = ru
    else:
        source = open_file(target)
    text = process_file(source)
    return count_frequency(text)


def print_stats(freq_dict, num):
    """
    Prints out most popular and least popular elements, separating these 2 lists
    If there is less than num*2 elements, prints them all out starting from most popular

    :param freq_dict: Dictionary with elements and their popularity
    :param num: Defines the number of most popular
    and least popular elements to print out
    :return:
    """
    sorted_list = [x for x in freq_dict.items()]
    sorted_list.sort(key=lambda x: str(x[0]))  # sort by key
    sorted_list.sort(key=lambda x: x[1], reverse=True)  # sort by value

    count = len(sorted_list)
    if count > num*2:
        for i in range(num):
            print("{}: {}".format(sorted_list[i][0], sorted_list[i][1]))
        print("\n...\n")
        for i in range(1, num+1):
            print("{}: {}".format(sorted_list[-i][0], sorted_list[-i][1]))
    else:
        for i in range(count):
            print("{}: {}".format(sorted_list[i][0], sorted_list[i][1]))


def main(filename):
    opened_file = open_file(filename)
    processed_file = process_file(opened_file)

    freq = count_frequency(processed_file)
    print_result(freq)

    print()
    words_freq = count_words(processed_file)
    print_words(words_freq)


if __name__ == '__main__':
    file_name = 'carcosa.txt'
    main(file_name)
