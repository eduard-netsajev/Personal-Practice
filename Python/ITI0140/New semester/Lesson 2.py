__author__ = 'Netšajev'

# First who completed the task in whole class. Got maximum points.


def brackets(word):
    print(word + ": ", end='')
    stack = []
    tags_open = ['(', '{', '[']
    tags_close = [')', '}', ']']
    for i in range(len(word)):
        if word[i] in tags_open:
            stack.append(word[i])
        elif word[i] in tags_close:
            if len(stack) > 0:
                if tags_close.index(word[i]) == tags_open.index(stack[-1]):
                    stack.pop()
                else:
                    print("wrong closing bracket at " + str(i))
                    return
            else:
                print("no opening bracket at " + str(i))
                return
        else:
            print("illegal symbol at " + str(i))
            return
    if len(stack) == 0:
        print("correct")
    else:
        print("no closing bracket at " + str(len(word)))


def main():
    while True:
        input_str = input("Sisend: ")
        brackets(input_str)

if __name__ == "__main__":
    main()