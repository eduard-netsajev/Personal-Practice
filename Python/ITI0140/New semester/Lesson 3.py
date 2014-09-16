__author__ = 'Netšajev'

# First who completed the task in whole class. Got maximum points.
# 4 for completing the task at the class
# 1 for completing the extra difficulty level task


def unpack(my_string):
    total = ""
    last_string = ""
    i = 0
    while i < len(my_string):
        if my_string[i].isalpha():
            last_string = my_string[i]
            total += last_string
        elif my_string[i].isnumeric():
            k = i
            while i < len(my_string)-1:
                if my_string[i+1].isnumeric():
                    i += 1
                else:
                    break
            multiplier = int(my_string[k:i+1])
            if multiplier == 0:
                total = total[0:-len(last_string)]
            else:
                total += last_string * (multiplier - 1)
        elif my_string[i] == '(':
            j = my_string.rfind(')')
            last_string = unpack(my_string[i+1:j])
            total += last_string
            i += j-i
        elif my_string[i] == ')':
            pass
        else:
            print("Illegal character: {}".format(my_string[i]))
            exit()
        i += 1
    return total


def main():
    while True:
        input_str = input("Sisend: ")
        print(unpack(input_str))


if __name__ == "__main__":
    main()