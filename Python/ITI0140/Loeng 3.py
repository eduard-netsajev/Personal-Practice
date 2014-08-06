__author__ = 'Netšajev'


def zip7(input_str):
    digits = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
    archive = False
    for char in input_str:
        if char in digits:
            archive = True
            break

    output = ""
    if archive:
        for i in range(0, len(input_str), 2):
            output += input_str[i]*int(input_str[i+1])
    else:
        current_char = None
        counter = 0
        for i in range(len(input_str)):
            if current_char == None:
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
    print(zip7(input("Sisend: ")))