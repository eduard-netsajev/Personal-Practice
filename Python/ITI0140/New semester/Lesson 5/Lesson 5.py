__author__ = 'Netšajev'


def process_file(opened_file):
    """
    Takes opened file as input.
    Removes non-alphabetic characters.
    Returns lowercase text with alphabetic
    characters and whitespaces only.

    Arguments:
    opened_file - opened file

    Returns:
    string - lowercase text without any non-alpha characters
    """
    result = ""
    for line in opened_file:
        line = line.lower()
        for letter in line:
            if letter.isalpha() or letter == " " or letter == "\n":
                result += letter
    return result


def process_words(processed_file):
    """
    Takes a string of text, which contains only
    whitespace and alphabetic characters as input.
    Returns tuple with 3 fields,
    where each field contains:
    - total words count
    - unique words count
    - unique words dictionary

    Arguments:
    processed_file - lowercase text without any non-alpha characters

    Returns:
    tuple - (integer total words, integer unique words, list of unique words)
    """
    unique_words = []
    words = processed_file.split()
    total = 0
    unique = 0
    for word in words:
        if word not in unique_words:
            unique += 1
            unique_words.append(word)
        total += 1
    return total, unique, unique_words


def files_conveier(files):
    """
    Takes list of filenames as input.
    Returns list of tuples,
    where each tuple contains:
    - total words count
    - unique words count
    - unique words dictionary

    Arguments:
    files - strings of file names

    Returns:
    list - list of tuples, 1 tuple for each file, where
    tuple - (integer total words, integer unique words, list of unique words)
    """

    result = []
    for file in files:
        f = open(file)
        result.append(process_words(process_file(f)))
        f.close()
    return result


def main():
    filename1 = "pg244.txt"
    filename2 = "pg3070.txt"
    results = files_conveier([filename1, filename2])
    print("Total words in text 1: " + str(results[0][0]))
    print("Total words in text 2: " + str(results[1][0]))
    print("Unique words in text 1: " + str(results[0][1]))
    print("Unique words in text 2: " + str(results[1][1]))
    print("Unique words sum in both files: " + str(results[0][1] + results[1][1]))
    first_text_set = set(results[0][2])
    second_text_set = set(results[1][2])
    print("Unique words that exist in text 1 but no in text 2: " + str(len(first_text_set - second_text_set)))
    print("Unique words that exist in text 2 but no in text 1: " + str(len(second_text_set - first_text_set)))
    print("Unique words that exist in both texts: " + str(len(second_text_set & first_text_set)))


if __name__ == "__main__":
    main()








