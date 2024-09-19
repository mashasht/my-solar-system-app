import axios from 'axios';

const API_URL = 'http://localhost:8080';

// Credentials for HTTP Basic Authentication
const username = 'user';
const password = 'password';
const token = btoa(`${username}:${password}`);  // Base64 encode the credentials

export const getPlanets = async () => {
    try {
        const response = await axios.get(`${API_URL}/planets`, {
            headers: {
                'Authorization': `Basic ${token}`
            }
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching planets:', error);
        throw error;
    }
};
