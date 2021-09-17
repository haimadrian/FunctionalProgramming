const expenseManagement = 'http://localhost:3000';
const userManagement = 'http://localhost:3001';
const expenseStatistics = 'http://localhost:3002';

const urls = {
    userSignUp: `${userManagement}/user/signup`,
    userSignOut: `${userManagement}/user/signout`,
    userInfo: `${userManagement}/user/info`
}

export default urls;