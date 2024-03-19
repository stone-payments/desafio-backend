-- relacao de users e transactions
select
t.id as transaction_id,
t.user_id as user_id,
u.name as user_name,
t.total_to_pay
from transactions t
inner join users u
on u.id = t.user_id
order by t.total_to_pay DESC;

-- quantidade de transações por user
select count(t.user_id), u.name
from transactions t
inner join users u
on u.id = t.user_id
group by u.name;

-- transações de um user
select u.name, t.id as transações from transactions t
inner join users u
on t.user_id = u.id
where t.user_id = '1616170b-db7a-365b-ad64-f21560a5ffb0';