function test()
    a = 2
    b = 30
    c = 0
    while a < b do
        print(c)
        print(a)
        a = 2 * a
        c = c + 1
    end

    if a > b - c then
        print(a)
    else
        print(b)
    end

    if c == b then
        print(c)
        print(b)
    end
end