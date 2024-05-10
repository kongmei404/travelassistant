-- Lua 脚本
local key = KEYS[1] -- 用户 ID + "orderID"
local orderListJson = ARGV[1] -- 订单列表 JSON 字符串
local expireTime = ARGV[2] -- 过期时间 (秒)

local orderList = cjson.decode(orderListJson)

for _, order in ipairs(orderList) do
  local orderId = tostring(order.orderId)
  local productId = tostring(order.productId)
  local orderJson = cjson.encode(order)
  redis.call("HSET", key, orderId .. productId, orderJson)
end

redis.call("EXPIRE", key, expireTime)

return 1 -- 返回成功
