function test()
    a = 2
    b = 30
    c = 0
    while a < b do
        print(a)
        a = 2 * a
        c = c + 1
    end

    if a > b then
        print a
    else
        print b
    end
end