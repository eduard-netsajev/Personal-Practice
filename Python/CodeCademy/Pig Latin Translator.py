__author__ = 'Netšajev'

word = input('Enter a word:')

if len(word) > 0 and word.isalpha():
    word = word.lower()
    word = word+word[0]+"ay"
    word = word[1:len(word)]
    print(word)
else:
    print('empty')