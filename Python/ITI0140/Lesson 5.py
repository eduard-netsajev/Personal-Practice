__author__ = 'Netšajev'


filename = 'carcosa.txt'


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
    result = {}
    for letter in processed_file:
        if letter.isalpha():
            if letter in result.keys():
                result[letter] += 1
            else:
                result[letter] = 1
    return result


def print_result(freq):
    for letter in sorted(freq.keys(), key=freq.get, reverse=True):
        print("%s: %d" % (letter, freq[letter]))


def count_words(processed_file):
    result = {}
    words = processed_file.split()
    for word in words:
        if word in result.keys():
            result[word] += 1
        else:
            result[word] = 1
    return result


def print_words(freq):
    sorted_list = [x for x in freq.items()]
    sorted_list.sort(key=lambda x: str(x[0]))  # sort by key
    sorted_list.sort(key=lambda x: x[1], reverse=True)  # sort by value

    count = len(sorted_list)
    if count > 10:
        count = 10
    for i in range(count):
        print("{}: {}".format(sorted_list[i][0], sorted_list[i][1]))


opened_file = open_file(filename)
processed_file = process_file(opened_file)

freq = count_frequency(processed_file)
print_result(freq)

print()
words_freq = count_words(processed_file)
print_words(words_freq)