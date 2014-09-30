__author__ = 'Netšajev'


def process_file(file):
    """
    Takes opened file as input.
    Removes non-alphabetic characters.
    Returns list of lowercase words.

    Arguments:
    opened_file - opened file

    Returns:
    list - lowercase text words without any non-alpha characters
    """
    result = []
    for line in file:
        line = line.lower()
        word = ""
        for letter in line:
            if letter.isalpha():
                word += letter
            elif len(word) > 0:
                result.append(word)
                word = ""
            else:
                word = ""
        if len(word) > 0:
            result.append(word)
    return result


def count_words(lst):
    unique_words = {}
    for word in lst:
        if word not in unique_words:
            unique_words[word] = 1
        else:
            unique_words[word] += 1
    return unique_words


def find_top_words(dct, n):
    pop_dict = {}
    for word in dct:
        size = len(word)
        if size in pop_dict:
            if len(pop_dict[size]) < n:
                pop_dict[size].append((word, dct[word]))
        else:
            pop_dict[size] = []
            pop_dict[size].append((word, dct[word]))
    return pop_dict


def print_top_words(dct, file):
    sorted_list = [dct[x] for x in dct]
    for lst in sorted_list:
        lst.sort(key=lambda x: x[0])  # sort by word
        lst.sort(key=lambda x: x[1], reverse=True)  # sort by count

    sorted_list.sort(key=lambda x: len(x[0]))  # sort by length

    file.write("|{:^8s}".format("pikkus"))
    file.write("|{:^18s}".format("word")+"|{:^8s}|".format("count"))
    file.write("\n")

    for i in range(len(sorted_list)):
        length = len(sorted_list[i][0][0])
        for word in sorted_list[i]:
            file.write("|{:^8s}".format(str(length)))
            file.write("|{:^18s}".format(word[0])+"|{:^8s}|".format(str(word[1])))
            file.write("\n")
