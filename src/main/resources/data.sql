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
    (1, 0.05, 38000.57, '2022-02-02'),
    (1, 0.02, 36000.01, '2022-02-02'),
    (1, 0.03, 37500, '2022-02-02'),
    (2, 0.2, 3200, '2022-02-02'),
    (2, 0.1, 2700, '2022-02-02'),
    (2, 0.2, 2500, '2022-02-02'),
    (3, 350, 1, '2022-02-02'),
    (4, 1.2, 420, '2022-02-02'),
    (10, 210, 0.11, '2022-02-02');