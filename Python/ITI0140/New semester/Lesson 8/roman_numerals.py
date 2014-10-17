"""
Pretty crappy and buggy Roman numeral converter

@author Cody McPhail 
"""


def convert(input):
    """
    A smaller number in front of a larger number means subtraction, all else means addition.
    For example, IV means 4, VI means 6.

    You would not put more than one smaller number in front of a larger number to subtract.
    For example, IIV would not mean 3.

    You must separate ones, tens, hundreds, and thousands as separate items.
    That means that 99 is XCIX, 90 + 9, but never should be written as IC.
    Similarly, 999 cannot be IM and 1999 cannot be MIM.
    """
    if type(input) == str:
        if len(input) == 0:
            return 0
        input = input.upper()
        if len(input.replace("M", "").replace("D", "").replace("C", "").replace("L", "").replace("X", "").replace("V", "").replace("I", "")) != 0:
            return -1
        sum = 0
        if input.find("IIIII") != -1 or input.find("VV") != -1 or input.find("XXXXX") != -1 or \
        input.find("DD") != -1 or input.find("CCCCC") != -1:
            return -1
        if input.find("IIV") != -1 or input.find("IIX") != -1 or input.find("IL") != -1 or \
        input.find("IC") != -1 or input.find("ID") != -1 or input.find("IM") != -1 \
        or check_separation(input):
            return -1
        for i, char in enumerate(input):
            if char == 'M':
                if larger_number_after(i, char, input):
                    sum = sum - 1000
                else:
                    sum = sum + 1000
            elif char == 'D':
                if larger_number_after(i, char, input):
                    sum = sum - 500
                else:
                    sum = sum + 500
            elif char == 'C':
                if larger_number_after(i, char, input):
                    sum = sum - 100  # Mistake, was sum -= sum - 100
                else:
                    sum = sum + 100
            elif char == 'L':
                if larger_number_after(i, char, input):
                    sum = sum - 50
                else:
                    sum = sum + 50
            elif char == 'X':
                if larger_number_after(i, char, input):
                    sum = sum - 10
                else:
                    sum = sum + 10
            elif char == 'V':
                if larger_number_after(i, char, input):
                    sum = sum - 5
                else:
                    sum = sum + 5
            elif char == 'I':
                if larger_number_after(i, char, input):
                    sum = sum - 1
                else:
                    sum = sum + 1
        return sum
    return -1


def check_separation(string):
    numerals = [("I", 1), ("V", 5), ("X", 10), ("L", 50), ("C", 100), ("D", 500), ("M", 1000)]
    idx = {'I': 0, 'V': 1, 'X': 2, 'L': 3, 'C': 4, 'D': 5, 'M': 6}
    if len(string) < 3:
        return False
    for i, char in enumerate(string):
        if i < len(string)-2:
            if string[i+2] == char and larger_number_after(i+1, char, string):
                return True
            for j in range(idx[char]+1, len(numerals)):
                if j < len(string)-1 and char == 'I':
                    if string[j+1] == "M" and string[j] == "C" or \
                    string[j+1] == "X" and string[j] == "C" or \
                    string[j+1] == "L" and string[j] == "X":
                        pass
                    elif string[j+1] == numerals[j][0]:
                        pass
                pass
    return False


def larger_number_after(i, char, string):
    numerals = {'M': 1000, 'D': 500, 'C': 100, 'L': 50, 'X': 10, 'V': 5, 'I': 1}
    for i in range(i, len(string)-1):
        if numerals[string[i+1]] > numerals[char]:
            return True
    return False


def main():
    print(convert("IXIX"))
    print(convert("I"))
    print(convert("IXIX"))
    print(convert("XX"))
    print(convert("MMMMM"))


numeral_map = tuple(zip(
    (1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1),
    ('M', 'CM', 'D', 'CD', 'C', 'XC', 'L', 'XL', 'X', 'IX', 'V', 'IV', 'I')
))


def int_to_roman(i):
    result = []
    for integer, numeral in numeral_map:
        count = i // integer
        result.append(numeral * count)
        i -= integer * count
    return ''.join(result)


def roman_to_int(n):
    i = result = 0
    for integer, numeral in numeral_map:
        while n[i:i + len(numeral)] == numeral:
            result += integer
            i += len(numeral)
    return result


def new_main():
    # Tested with until 125000 - 100% identical results
    ok = 0
    until = 5000
    for i in range(1, until):
        roman_string = str(int_to_roman(i))
        print(roman_string)
        if (convert(roman_string)) == i:
            # print(i, roman_string, convert(roman_string))
            ok += 1
        else:
            print("Vale!")
            print(i, roman_string, convert(roman_string))
            assert False

    result = 1.0 * ok / (until - 1) * 100
    print("{}% correct".format(result))

if __name__ == "__main__":
    main()