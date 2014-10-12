def de_bruijn(n):
    """
    De Bruijn sequence for alphabet [0, 1]
    and subsequences of length n.
    """
    k = 2
    a = [0] * k * n
    sequence = []
    def db(t, p):
        if t > n:
            if n % p == 0:
                for j in range(1, p + 1):
                    sequence.append(a[j])
        else:
            a[t] = a[t - p]
            db(t + 1, p)
            for j in range(a[t - p] + 1, k):
                a[t] = j
                db(t + 1, t)
    db(1, 1)
    print("".join([str(x) for x in sequence]))

de_bruijn(3)