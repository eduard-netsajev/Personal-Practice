__author__ = 'Netšajev'


def zip7(input_str):
    for char in input_str:
        if char.isdigit():
            archive = True
            break
    else:
        archive = False
    output = ""
    if archive:
        for i in range(0, len(input_str), 2):
            output += input_str[i]*int(input_str[i+1])
        return output
    else:
        current_char = None
        counter = 0
        for i in range(len(input_str)):
            if current_char is None:
                current_char = input_str[i]
                counter += 1
            elif current_char == input_str[i]:
                counter += 1
            else:
                output += current_char+str(counter)
                current_char = input_str[i]
                counter = 1
        else:
            output += current_char+str(counter)

        if len(output) < len(input_str):
            return output
        else:
            return input_str


while True:
    user_str = input("Sisend: ")
    if user_str == 'exit':
        break
    print(zip7(user_str))