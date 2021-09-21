const expenseManagement = 'http://localhost:9000';
const userManagement = 'http://localhost:9001';
const expenseStatistics = 'http://localhost:9002';

const urls = {
    userSignUp: `${userManagement}/api/user/signup`,
    userSignOut: `${userManagement}/api/user/signout`,
    userInfo: `${userManagement}/api/user/info`
    expenseFetch: `${expenseManagement}/api/expense/fetch`,
    totalDataCount: `${expenseManagement}/api/expense/count`,
    addExpense: `${expenseManagement}/api/expense`
}

export default urls;