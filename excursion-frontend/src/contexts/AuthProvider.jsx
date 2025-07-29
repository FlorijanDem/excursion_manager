import { api, clearAuth, setAuth } from "@/utils/api";
import { ApiError } from "@/utils/apiError";
import { jwtDecode } from "jwt-decode";
import { createContext, useContext, useState, useMemo } from "react";
import { useNavigate } from "react-router";

const AuthContext = createContext(null);

export const Authprovider = ({ children }) => {
  const navigate = useNavigate();

  const [account, setAccount] = useState(() => {
    const jwt = localStorage.getItem("jwt");
    if (jwt) {
      const decodedjwt = jwtDecode(jwt);
      if (decodedjwt.exp * 1000 < Date.now()) {
        return null;
      }
      return decodedjwt;
    }
    return null;
  });

  const login = async (data) => {
    try {
      const apiResponse = await api.post("auth/token", data);
      const jwt = apiResponse.data.data;
      setAccount(jwtDecode(jwt));
      setAuth(jwt);
      navigate("/");
    } catch (error) {
      const errorMessage =
        error?.response?.data?.message ?? error?.message ?? "Unknown error";
      const errorStatus = error.response?.status;
      throw new ApiError(errorMessage, errorStatus);
    }
  };

  const register = async ({ email, password }) => {
    return await api.post("auth/register", {
      email,
      password,
    });
  };

  const logout = () => {
    setAccount(null);
    clearAuth();
    localStorage.removeItem("jwt");
    navigate("/home");
  };

  const isLoggedIn = !!account;

  const value = useMemo(() => ({
    account, register, login, logout, isLoggedIn
  }), [account, register, login, logout]);
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  return useContext(AuthContext);
};
