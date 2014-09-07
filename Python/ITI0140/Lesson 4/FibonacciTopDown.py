__author__ = 'Netšajev'

fibs = [0, 1]

def fibTopDown(n):
    # This loop ensures we don't run in stack overflows
    for i in range(0, n+1, 500):  # delta is up to you
        fibTopDownR(i)
    return fibTopDownR(n)


def fibTopDownR(n):
    if n > len(fibs) - 1:
        res = (fibTopDownR(n-1)+fibTopDownR(n-2))
        fibs.insert(n, res)
    else:
        res = fibs[n]
    return res

print(str(fibTopDown(7777)))