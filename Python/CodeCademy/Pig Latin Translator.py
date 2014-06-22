__author__ = 'Netšajev'


def translator():
    word = input('Enter a word:')
    if len(word) > 0 and word.isalpha():
        word = word.lower()
        word = word+word[0]+"ay"
        word = word[1:len(word)]
        print(word)
        translator()
    else:
        print('empty')

translator()