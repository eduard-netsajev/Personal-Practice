__author__ = 'Netšajev'


def double_char(str_in):
    """
    Given a string, return a string where for every
    char in the original, there are two chars.

    double_char('The') → 'TThhee'
    double_char('AAbb') → 'AAAAbbbb'
    double_char('Hi-There') → 'HHii--TThheerree'
    """
    result = ""
    for char in str_in:
        result += char * 2
    return result


def count_hi(str_in):
    """
    Return the number of times that the string
    "hi" appears anywhere in the given string.

    count_hi('abc hi ho') → 1
    count_hi('ABChi hi') → 2
    count_hi('hihi') → 2
    """
    counter = 0
    for i in range(1, len(str_in)):
        if str_in[i] == 'i'and str_in[i-1] == 'h':
                counter += 1
    return counter


def cat_dog(str_in):
    """
    Return True if the string "cat" and "dog" appear
    the same number of times in the given string.

    cat_dog('catdog') → True
    cat_dog('catcat') → False
    cat_dog('1cat1cadodog') → True
    """
    dog_counter = 0
    cat_counter = 0
    for i in range(2, len(str_in)):
        if str_in[i] == 'g' and str_in[i-1] == 'o' and str_in[i-2] == 'd':
            dog_counter += 1
        elif str_in[i] == 't' and str_in[i-1] == 'a' and str_in[i-2] == 'c':
            cat_counter += 1
    return dog_counter == cat_counter


def count_code(str_in):
    """
    Return the number of times that the string "code"
    appears anywhere in the given string, except we'll
    accept any letter for the 'd', so "cope" and "cooe" count.

    count_code('aaacodebbb') → 1
    count_code('codexxcode') → 2
    count_code('cozexxcope') → 2
    """
    counter = 0
    for i in range(3, len(str_in)):
        if str_in[i] == 'e' and str_in[i-2] == 'o' and str_in[i-3] == 'c':
            counter += 1
    return counter


def end_other(a, b):
    """
    Given two strings, return True if either of the strings
    appears at the very end of the other string,
    ignoring upper/lower case differences (in other words,
    the computation should not be "case sensitive").
    Note: s.lower() returns the lowercase version of a string.

    end_other('Hiabc', 'abc') → True
    end_other('AbC', 'HiaBc') → True
    end_other('abc', 'abXabc') → True
    """
    a, b = a.lower(), b.lower()
    short = len(a)
    if len(b) < short:
        short = len(b)

    for i in range(-short, 0):
        if a[i] != b[i]:
            return False
    return True


def xyz_there(str_in):
    """
    Return True if the given string contains an appearance
    of "xyz" where the xyz is not directly preceeded by
    a period (.). So "xxyz" counts but "x.xyz" does not.

    xyz_there('abcxyz') → True
    xyz_there('abc.xyz') → False
    xyz_there('xyz.abc') → True
    """
    count = str_in.count("xyz")
    index = len(str_in)
    for i in range(count):
        index = str_in.rfind("xyz", 0, index)
        if index > 0:
            if str_in[index-1] == '.':
                continue
        return True
    return False