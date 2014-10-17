__author__ = 'Netšajev'


def gen_word(opened_file):
    """Generate next word"""
    result = ""
    for line in opened_file:
        for char in line:
            if char.isalpha():
                result += char
            else:
                word = result
                if len(word) > 0:
                    result = ""
                    yield word


def main():
    filename = 'pg3070.txt'
    my_file = open(filename)
    words_list = [word for word in gen_word(my_file)][0:400:4]
    my_file.close()
    avg_word = len("".join(words_list))/len(words_list)
    filtered_list = list(filter(lambda x: len(x) > avg_word, words_list))
    print(avg_word)
    for each_word in filtered_list:
        print(each_word.capitalize())

if __name__ == "__main__":
    main()