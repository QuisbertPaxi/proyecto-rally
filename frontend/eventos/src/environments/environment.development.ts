export const environment = {

    HOST_URL: 'http://localhost:19090/',

    API_URL: 'http://localhost:19090/api/v1/',

    decodeToken : function (token: string): any
    {
        const payload = token.split(".")[1];
        return JSON.parse(atob(payload));
    }
};
