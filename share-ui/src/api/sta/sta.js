import request from '@/utils/request'

// 统计用户注册数据
export function getUserCount() {
    return request({
        url: '/sta/userCount',
        method: 'get'
    })
}
