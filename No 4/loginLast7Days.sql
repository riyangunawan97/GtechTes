SELECT customerId, loginTime
FROM Customer
WHERE loginTime >= DATEADD(day,-7, GETDATE())
