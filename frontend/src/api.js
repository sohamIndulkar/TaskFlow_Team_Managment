import axios from 'axios';
const api=axios.create({baseURL:import.meta.env.VITE_API_URL||'http://localhost:8080/api'});
api.interceptors.request.use(config=>{const token=localStorage.getItem('tf_access');if(token)config.headers.Authorization=`Bearer ${token}`;return config;});
let refreshing;
api.interceptors.response.use(r=>r,async error=>{const original=error.config;if(error.response?.status===401&&!original._retry&&!original.url?.includes('/auth/')){original._retry=true;try{refreshing??=axios.post(`${api.defaults.baseURL}/auth/refresh`,{refreshToken:localStorage.getItem('tf_refresh')}).then(({data})=>{localStorage.setItem('tf_access',data.accessToken);localStorage.setItem('tf_refresh',data.refreshToken);return data.accessToken;}).finally(()=>{refreshing=null;});original.headers.Authorization=`Bearer ${await refreshing}`;return api(original);}catch{localStorage.removeItem('tf_access');localStorage.removeItem('tf_refresh');window.dispatchEvent(new Event('taskflow:logout'));}}return Promise.reject(error);});
export default api;
