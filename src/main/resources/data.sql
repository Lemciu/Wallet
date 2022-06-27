INSERT INTO stock
    (symbol, favourite)
VALUES
    ('BTC', true),
    ('ETH', true),
    ('USDT', false),
    ('BNB', false),
    ('USDC', false),
    ('XRP', false),
    ('ADA', true),
    ('BUSD', false),
    ('SOL', false),
    ('DOGE', false),
    ('DOT', false),
    ('WBTC', false),
    ('TRX', false),
    ('DAI', false),
    ('AVAX', false),
    ('SHIB', false),
    ('MATIC', false),
    ('LEO', false),
    ('CRO', false),
    ('LTC', false),
    ('NEAR', false),
    ('UNI', false),
    ('XLM', false),
    ('FTT', false),
    ('BCH', false),
    ('XMR', false),
    ('LINK', false),
    ('ETC', false),
    ('ATOM', false),
    ('ALGO', false),
    ('FLOW', false),
    ('VET', false),
    ('ICP', false),
    ('HBAR', false),
    ('MANA', false),
    ('APE', false),
    ('XTZ', false),
    ('KCS', false),
    ('EGLD', false),
    ('SAND', false),
    ('FIL', false),
    ('AAVE', false),
    ('TUSD', false),
    ('ZEC', false),
    ('EOS', false),
    ('AXS', false),
    ('THETA', false),
    ('KLAY', false),
    ('MKR', false),
    ('HT', false);

INSERT INTO stock_transaction
(stock_id, amount, price, date, type)
VALUES
    (1, 0.0577235, 40000.0, '2022-04-22', 'BUY'),
    (1, 0.048543, 33000.0, '2021-07-16', 'BUY'),
    (1, 0.051212355, 30000.0, '2022-05-11', 'BUY'),
    (2, 0.553544, 1891.0, '2022-04-22', 'BUY'),
    (2, 0.2422, 1812.0, '2022-04-22', 'BUY'),
    (2, 0.1442, 2321.0, '2022-04-22', 'BUY'),
    (2, 0.2127422, 2142.0, '2022-04-22', 'BUY'),
    (7, 300.0, 0.52321, '2022-04-22', 'BUY'),
    (7, 300.0, 0.3911, '2022-04-22', 'BUY'),
    (7, 300.0, 0.4376, '2022-04-22', 'BUY'),
    (7, 300.0, 0.5106, '2022-04-22', 'BUY'),
    (7, 300.0, 0.5276, '2022-04-22', 'BUY'),
    (4, 1, 252.132, '2022-04-22', 'BUY'),
    (4, 0.5, 271.542, '2022-04-22', 'BUY'),
    (4, 0.5, 287.174, '2022-04-22', 'BUY'),
    (4, 1, 310.174, '2022-04-22', 'BUY'),
    (4, 0.25, 376.456, '2022-04-22', 'BUY'),
    (4, 0.25, 376.534, '2022-04-22', 'BUY'),
    (4, 0.4, 364.1908, '2022-04-22', 'BUY'),
    (10, 850, 0.010132, '2022-04-22', 'BUY'),
    (10, 5000, 0.009645, '2022-04-22', 'BUY'),
    (10, 2500, 0.014053, '2022-04-22', 'BUY'),
    (2, 0.4, 1200, '2022-06-12', 'SWAP'),
    (1, -0.05, 25000, '2022-06-12', 'SWAP');

INSERT INTO swap_transaction
    (bought_stock_transaction_id, sold_stock_transaction_id)
VALUES
    (23, 24);


INSERT INTO budget_transaction
(transaction_value, title, description, type, date)
VALUES
    (15000, 'Sprzedaż auta','Sprzedanie starego samochodu' , 'INCOME', '2022-03-13'),
    (3000, 'Wypłata','wypłata za maj + odebranie nadgodzin' , 'INCOME', '2022-05-10'),
    (3000, 'Wypłata','wypłata za kwiecień' , 'INCOME', '2022-04-10'),
    (214, 'Zakupy spożywcze','biedra' ,  'EXPENSE', '2022-06-02'),
    (150, 'Prezent dla Wojtka','Zdalnie sterowany samochodzik' ,  'EXPENSE', '2022-04-07'),
    (410, 'Paliwo','400zł za bak... co za czasy' ,  'EXPENSE', '2022-04-28');