INSERT INTO stock
    (id, symbol, favourite)
VALUES
    (1,'BTC', true),
    (2, 'ETH', true),
    (3, 'USDT', false),
    (4,'BNB', false),
    (5, 'USDC', false),
    (6, 'XRP', false),
    (7, 'ADA', false),
    (8, 'BUSD', false),
    (9, 'SOL', false),
    (10, 'DOGE', false);

INSERT INTO transaction
    (stock_id, amount, buy_price, date)
VALUES
    (1, 0.01, 38000.57, '2022-02-02'),
    (1, 0.02, 36000.01, '2022-02-02'),
    (1, 0.01, 37500, '2022-02-02'),
    (2, 0.05, 3200, '2022-02-02'),
    (2, 0.1, 2700, '2022-02-02'),
    (2, 0.2, 2500, '2022-02-02'),
    (3, 350, 1, '2022-02-02'),
    (4, 1.2, 420, '2022-02-02'),
    (10, 210, 0.11, '2022-02-02');

INSERT INTO notification
    (title, description, date, periodic)
VALUES
    ('Opłacić czynsz', 'Mieszkanie - 1000zł, Parking - 170zł','2022-05-10', true),
    ('Spotkanie FED', 'Rozmowy o stopach procentowych w Stanach czy inne pierdoły','2022-05-13', false),
    ('Oddać hajs', 'Stówkę Zenkowi oddać','2022-06-03', false),
    ('Dostać przelew od krissa','2850zł' ,'2022-05-30', false);