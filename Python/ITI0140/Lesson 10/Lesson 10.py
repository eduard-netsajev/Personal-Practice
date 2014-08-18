__author__ = 'Netšajev'

"""
Ülesanne
Kirjutada generaatorfunktsioon carcosa.txt
faili peale, mis tagastaks uue sõna iga
järgneva väljakutse peale. Kasutades tehtud
generaatorfunktsiooni, genereerida järjend
(list comprehensioni abil), mis sisaldab igat
kolmandat sõna, kokku 100 tükki. Selle peale
kirjutada lambda-funktsiooniga filter, mis
väljastaks sellest listist sõnad, mille pikkus on
suurem kui andtud listi keskmine sõnapikkus.
Väljastada sõnad suure algustähega.
"""


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


filename = 'carcosa.txt'
my_file = open(filename)

words_list = [word for word in gen_word(my_file)][0:300:3]
avg_word = len("".join(words_list))/len(words_list)  # Although can be in the lambda, not good performance wise
filtered_list = list(filter(lambda x: len(x) <= avg_word, words_list))

for each_word in filtered_list:
    print(each_word.capitalize())