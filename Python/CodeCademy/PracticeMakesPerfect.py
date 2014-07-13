__author__ = 'Netšajev'


def is_even(x):
    if x % 2:
        return False
    else:
        return True


def is_int(x):
    if x-int(x):
        return False
    else:
        return True


def digit_sum(x):
    x = abs(x)
    x = str(x)
    dsum = 0
    for c in x:
        dsum += int(c)
    return dsum


def factorial(x):
    if x == 0:
        return 1
    else:
        return x*factorial(x-1)


def is_prime(x):
    if x < 2:
        return False
    for i in range(2, x):
        if x % i == 0:
            return False
    return True


def reverse(text):
    if len(text):
        return reverse(text[1:])+text[0]
    return text


def anti_vowel(text):
    vowels = set('eEaAoOuUiI')
    for char in vowels:
        text = text.replace(char, '')
    return text


score = {"a": 1, "c": 3, "b": 3, "e": 1, "d": 2, "g": 2,
         "f": 4, "i": 1, "h": 4, "k": 5, "j": 8, "m": 3,
         "l": 1, "o": 1, "n": 1, "q": 10, "p": 3, "s": 1,
         "r": 1, "u": 1, "t": 1, "w": 4, "v": 4, "y": 4,
         "x": 8, "z": 10}


def scrabble_score(word):
    word = word.lower()
    total = 0
    for c in word:
        total += score[c]
    return total


def censor(text, word):
    return text.replace(word, "*"*len(word))


def count(sequence, item):
    counter = 0
    for thing in sequence:
        if thing == item:
            counter += 1
    return counter


def purify(numbers):
    evenlist = []
    for num in numbers:
        if not num % 2:
            evenlist.append(num)
    return evenlist


def product(ints):
    total = 1
    for num in ints:
        total *= num
    return total


def remove_duplicates(lst):
    unique = []
    for item in lst:
        if item not in unique:
            unique.append(item)
    return unique


def median(numbers):
    numbers = sorted(numbers)
    n = len(numbers)
    if n%2:
        return numbers[n // 2]
    else:
        return (numbers[n/2-1]+numbers[n/2])/2.0


def main():
    """ test """

    if is_even(int(input("Check for evenness number: "))):
        print("Even")
    else:
        print("Odd")

    if is_int(float(input("Check for int or float number: "))):
        print("Int")
    else:
        print("Float")

    print("The sum of your input is {}".format(digit_sum(int(input("Find the sum of numbers in: ")))))

    print("Factorial of your number is {}".format(factorial(int(input("Find the factorial of number: ")))))

    if is_prime(int(input("Check for prime number: "))):
        print("Prime")
    else:
        print("Not prime")

    txt = input("Type a sentence: ")
    print("The reverse variant is: \n{}".format(reverse(txt)))
    print("The variant without vowels is: \n{}".format(anti_vowel(txt)))

    print("The scrabble score of the word is {}".format(scrabble_score(txt)))

if __name__ == "__main__":
    main()