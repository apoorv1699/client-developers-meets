const App = (() => {
  const state = {
    role: "client"
  };

  const API_BASE = "/api";
  const avatarPalette = ["#0f766e", "#f59e0b", "#1d4ed8", "#db2777", "#16a34a", "#ea580c"];
  const portfolioPalette = ["palette-teal", "palette-coral", "palette-indigo", "palette-olive"];

  const qs = (selector, scope = document) => scope.querySelector(selector);
  const qsa = (selector, scope = document) => Array.from(scope.querySelectorAll(selector));

  const getQueryParam = (key) => {
    const params = new URLSearchParams(window.location.search);
    return params.get(key);
  };

  const getRole = () => {
    return localStorage.getItem("cd-role") || "client";
  };

  const getStoredNumber = (key, fallback) => {
    const value = Number(localStorage.getItem(key));
    return Number.isFinite(value) && value > 0 ? value : fallback;
  };

  const getActiveUserId = () => getStoredNumber("cd-user-id", 1);
  const getActiveDeveloperId = () => {
    const fromQuery = Number(getQueryParam("id"));
    if (Number.isFinite(fromQuery) && fromQuery > 0) return fromQuery;
    return getStoredNumber("cd-developer-id", 1);
  };
  const getActiveClientId = () => getStoredNumber("cd-client-id", 1);
  const setStoredNumber = (key, value) => {
    if (!Number.isFinite(value) || value <= 0) return;
    localStorage.setItem(key, String(value));
  };

  const setRole = (role) => {
    if (!role) return;
    state.role = role;
    localStorage.setItem("cd-role", role);
    qsa("[data-role-chip]").forEach((chip) => {
      chip.textContent = role === "developer" ? "Developer" : "Client";
    });
  };

  const routeToHome = () => {
    const role = getRole();
    window.location.href = role === "developer" ? "/developer/home" : "/client/home";
  };

  const apiFetch = async (path, options = {}) => {
    const response = await fetch(`${API_BASE}${path}`, {
      headers: {
        "Content-Type": "application/json"
      },
      ...options
    });
    if (!response.ok) {
      throw new Error(`API error ${response.status}`);
    }
    return response.json();
  };

  const getInitials = (name = "") => {
    return name
      .split(" ")
      .filter(Boolean)
      .map((word) => word[0])
      .join("")
      .slice(0, 2)
      .toUpperCase();
  };

  const toUiDeveloper = (dev, index = 0) => {
    const skills = Array.isArray(dev.skills)
      ? dev.skills
      : dev.skills
      ? dev.skills.split(",").map((skill) => skill.trim()).filter(Boolean)
      : [];
    return {
      ...dev,
      skills,
      rating: dev.rating || 4.8,
      initials: dev.initials || getInitials(dev.name),
      avatarColor: dev.avatarColor || avatarPalette[index % avatarPalette.length]
    };
  };

  const toUiPortfolio = (project, index = 0) => {
    const techStack = Array.isArray(project.techStack)
      ? project.techStack
      : project.techStack
      ? project.techStack.split(",").map((tech) => tech.trim()).filter(Boolean)
      : [];
    return {
      ...project,
      techStack,
      palette: project.palette || portfolioPalette[index % portfolioPalette.length]
    };
  };

  const createAvatar = (item, size = "avatar") => {
    const initials = item.initials || getInitials(item.name) || "CD";
    const color = item.avatarColor || avatarPalette[0];
    return `<div class="${size}" style="background:${color}">${initials}</div>`;
  };

  const createDevCard = (dev, withLink = true) => {
    const skills = dev.skills.slice(0, 3).map((skill) => `<span class="pill">${skill}</span>`).join("");
    const link = withLink ? `href="/client/developer?id=${dev.id}"` : "href=\"#\"";
    return `
      <div class="card dev-card">
        <div class="dev-header">
          ${createAvatar(dev)}
          <div>
            <div class="card-title">${dev.name}</div>
            <div class="muted">${dev.title}</div>
          </div>
        </div>
        <div class="dev-meta">
          <span class="pill">${dev.location}</span>
          <span class="pill">${dev.rate}</span>
          <span class="rating">&#9733; ${dev.rating}</span>
        </div>
        <div class="tag-list">${skills}</div>
        <div class="muted">${dev.availability}</div>
        <a class="btn btn-secondary" ${link}>View profile</a>
      </div>
    `;
  };

  const renderDevelopers = (containerId, list) => {
    const container = qs(containerId);
    if (!container) return;
    container.innerHTML = list.map((dev) => createDevCard(dev)).join("");
  };

  const renderPortfolio = (containerId, list) => {
    const container = qs(containerId);
    if (!container) return;
    container.innerHTML = list
      .map(
        (project) => `
          <div class="portfolio-card">
            <div class="portfolio-cover ${project.palette}"></div>
            <div class="card-title">${project.title}</div>
            <div class="muted">${project.description}</div>
            <div class="tag-list">${project.techStack.map((tech) => `<span class="pill">${tech}</span>`).join("")}</div>
          </div>
        `
      )
      .join("");
  };

  const initLanding = () => {
    if (document.body.dataset.page !== "landing") return;
    qsa("[data-role-select]").forEach((btn) => {
      btn.addEventListener("click", () => {
        setRole(btn.dataset.role);
      });
    });
  };

  const initLogin = () => {
    if (document.body.dataset.page !== "login") return;
    const roleFromQuery = getQueryParam("role");
    const role = roleFromQuery || getRole();
    setRole(role);

    const authTabs = qsa("[data-auth-tab]");
    const authViews = qsa("[data-auth-view]");
    const setAuthView = (view) => {
      authTabs.forEach((btn) => btn.classList.toggle("is-active", btn.dataset.authTab === view));
      authViews.forEach((panel) => {
        panel.style.display = panel.dataset.authView === view ? "block" : "none";
      });
    };
    authTabs.forEach((btn) => {
      btn.addEventListener("click", () => setAuthView(btn.dataset.authTab));
    });
    setAuthView("login");

    const roleButtons = qsa("[data-role]");
    const roleSections = qsa("[data-role-fields]");
    const updateRoleView = (value) => {
      roleButtons.forEach((btn) => {
        if (!btn.dataset.role) return;
        btn.classList.toggle("is-active", btn.dataset.role === value);
      });
      roleSections.forEach((section) => {
        section.style.display = section.dataset.roleFields === value ? "block" : "none";
      });
    };
    updateRoleView(role);
    roleButtons.forEach((btn) => {
      if (!btn.dataset.role) return;
      btn.addEventListener("click", () => {
        setRole(btn.dataset.role);
        updateRoleView(btn.dataset.role);
      });
    });

    const loginForm = qs("#login-form");
    if (loginForm) {
      loginForm.addEventListener("submit", (event) => {
        event.preventDefault();
        const emailInput = loginForm.querySelector('input[name="email"]');
        const passwordInput = loginForm.querySelector('input[name="password"]');
        const email = emailInput ? emailInput.value.trim() : "";
        const password = passwordInput ? passwordInput.value.trim() : "";
        if (!email || !password) {
          alert("Please enter your email and password.");
          return;
        }
        apiFetch("/auth/login", {
          method: "POST",
          body: JSON.stringify({ email, password })
        })
          .then((data) => {
            setRole(data.role.toLowerCase());
            setStoredNumber("cd-user-id", data.userId);
            if (data.developerId) setStoredNumber("cd-developer-id", data.developerId);
            if (data.clientId) setStoredNumber("cd-client-id", data.clientId);
            routeToHome();
          })
          .catch(() => {
            alert("Login failed. Please check your credentials.");
          });
      });
    }

    const signupForm = qs("#signup-form");
    if (signupForm) {
      signupForm.addEventListener("submit", (event) => {
        event.preventDefault();
        const activeRole = getRole();
        const email = qs("#signup-email")?.value?.trim();
        const password = qs("#signup-password")?.value?.trim();
        if (!email || !password) {
          alert("Email and password are required.");
          return;
        }
        const payload = {
          role: activeRole.toUpperCase(),
          email,
          password
        };
        if (activeRole === "client") {
          payload.companyName = qs("#company-name")?.value?.trim();
          payload.industry = qs("#client-industry")?.value?.trim();
          payload.budgetRange = qs("#client-budget")?.value?.trim();
        } else {
          payload.name = qs("#dev-name")?.value?.trim();
          payload.title = qs("#dev-title")?.value?.trim();
          payload.skills = qs("#dev-skills")?.value?.trim();
          payload.rate = qs("#dev-rate")?.value?.trim();
          payload.location = qs("#dev-location")?.value?.trim();
          payload.availability = qs("#dev-availability")?.value?.trim();
          payload.bio = qs("#dev-bio")?.value?.trim();
          payload.projectTitle = qs("#project-title")?.value?.trim();
          payload.projectDescription = qs("#project-description")?.value?.trim();
          payload.projectTechStack = qs("#project-stack")?.value?.trim();
          payload.projectImageUrl = qs("#project-image")?.value?.trim();
        }
        apiFetch("/auth/signup", {
          method: "POST",
          body: JSON.stringify(payload)
        })
          .then((data) => {
            setRole(data.role.toLowerCase());
            setStoredNumber("cd-user-id", data.userId);
            if (data.developerId) setStoredNumber("cd-developer-id", data.developerId);
            if (data.clientId) setStoredNumber("cd-client-id", data.clientId);
            routeToHome();
          })
          .catch((error) => {
            alert("Sign up failed. Please check your inputs.");
          });
      });
    }
  };

  const initClientHome = () => {
    if (document.body.dataset.page !== "client-home") return;
    apiFetch("/developers")
      .then((data) => {
        if (data.length) {
          const uiData = data.map(toUiDeveloper);
          setStoredNumber("cd-developer-id", uiData[0].id);
          renderDevelopers("#client-recommended", uiData.slice(0, 3));
        } else {
          renderDevelopers("#client-recommended", MOCK.developers.slice(0, 3));
        }
      })
      .catch(() => {
        renderDevelopers("#client-recommended", MOCK.developers.slice(0, 3));
      });

    const clientId = getActiveClientId();
    apiFetch(`/clients/${clientId}`)
      .then((data) => {
        setStoredNumber("cd-client-id", data.id);
        qsa("[data-client-field]").forEach((input) => {
          const key = input.dataset.clientField;
          if (data[key]) input.value = data[key];
        });
      })
      .catch(() => {
        const fallback = { companyName: "Nimbus Health", industry: "Healthcare", budgetRange: "$20k-$30k" };
        qsa("[data-client-field]").forEach((input) => {
          const key = input.dataset.clientField;
          if (fallback[key]) input.value = fallback[key];
        });
      });

    const saveClientButton = qs("[data-save-client]");
    if (saveClientButton) {
      saveClientButton.addEventListener("click", () => {
        const payload = {};
        qsa("[data-client-field]").forEach((input) => {
          payload[input.dataset.clientField] = input.value;
        });
        const body = {
          userId: getActiveUserId(),
          companyName: payload.companyName || "Client Company",
          industry: payload.industry || "",
          budgetRange: payload.budgetRange || ""
        };
        const activeClientId = getActiveClientId();
        const request = activeClientId
          ? apiFetch(`/clients/${activeClientId}`, { method: "PUT", body: JSON.stringify(body) })
          : apiFetch("/clients", { method: "POST", body: JSON.stringify(body) });
        request
          .then((data) => {
            setStoredNumber("cd-client-id", data.id);
            alert("Client profile saved.");
          })
          .catch(() => {
            alert("Could not save client profile. Ensure a user exists in the database.");
          });
      });
    }
  };

  const initClientDevelopers = () => {
    if (document.body.dataset.page !== "client-developers") return;
    const listContainer = qs("#client-developer-list");
    if (!listContainer) return;

    const renderList = (items) => {
      listContainer.innerHTML = items.map((dev) => createDevCard(dev)).join("");
    };

    apiFetch("/developers")
      .then((data) => {
        const uiData = data.map(toUiDeveloper);
        renderList(uiData.length ? uiData : MOCK.developers);
        qsa("[data-skill]").forEach((tag) => {
          tag.addEventListener("click", () => {
            const active = tag.classList.contains("is-active");
            qsa("[data-skill]").forEach((t) => t.classList.remove("is-active"));
            if (active) {
              renderList(uiData.length ? uiData : MOCK.developers);
              return;
            }
            tag.classList.add("is-active");
            const skill = tag.dataset.skill;
            const source = uiData.length ? uiData : MOCK.developers;
            renderList(source.filter((dev) => dev.skills.includes(skill)));
          });
        });
      })
      .catch(() => {
        renderList(MOCK.developers);
      });
  };

  const initClientDeveloperProfile = () => {
    if (document.body.dataset.page !== "client-developer") return;
    const id = getQueryParam("id") || MOCK.developers[0].id;

    const renderProfile = (dev) => {
      const header = qs("#dev-profile-header");
      if (header) {
        header.innerHTML = `
          ${createAvatar(dev, "avatar avatar-lg")}
          <div>
            <div class="card-title">${dev.name}</div>
            <div class="muted">${dev.title || "Product Builder"}</div>
            <div class="dev-meta">
              <span class="pill">${dev.location || "Remote"}</span>
              <span class="pill">${dev.rate || "$50/hr"}</span>
              <span class="rating">&#9733; ${dev.rating || 4.8}</span>
            </div>
          </div>
        `;
      }

      const bio = qs("#dev-bio");
      if (bio) bio.textContent = dev.bio || "Developer bio will appear here.";

      const skills = qs("#dev-skills");
      if (skills) {
        skills.innerHTML = dev.skills.map((skill) => `<span class="pill">${skill}</span>`).join("");
      }

      const reviews = qs("#dev-reviews");
      if (reviews) {
        const list = MOCK.reviews.filter((review) => review.developerId === dev.id);
        reviews.innerHTML = list
          .map(
            (review) => `
              <div class="card">
                <div class="rating">&#9733; ${review.rating}</div>
                <div class="card-title">${review.clientName}</div>
                <div class="muted">${review.comment}</div>
              </div>
            `
          )
          .join("");
      }
    };

    apiFetch(`/developers/${id}`)
      .then((data) => {
        const uiDev = toUiDeveloper(data);
        renderProfile(uiDev);
        setStoredNumber("cd-developer-id", uiDev.id);
        return apiFetch(`/developers/${id}/portfolio`);
      })
      .then((projects) => {
        const uiProjects = projects.map(toUiPortfolio);
        renderPortfolio("#dev-portfolio", uiProjects);
      })
      .catch(() => {
        const dev = MOCK.developers.find((item) => item.id === id) || MOCK.developers[0];
        renderProfile(dev);
        const portfolios = MOCK.portfolios.filter((item) => item.developerId === dev.id);
        renderPortfolio("#dev-portfolio", portfolios);
      });

    const inquiryButton = qs("[data-send-inquiry]");
    const inquiryInput = qs("[data-inquiry-summary]");
    if (inquiryButton && inquiryInput) {
      inquiryButton.addEventListener("click", () => {
        const summary = inquiryInput.value.trim();
        if (!summary) return;
        apiFetch("/inquiries", {
          method: "POST",
          body: JSON.stringify({
            clientId: getActiveClientId(),
            developerId: Number(id),
            projectSummary: summary,
            status: "New"
          })
        })
          .then(() => {
            inquiryInput.value = "";
            alert("Inquiry sent.");
          })
          .catch(() => {
            alert("Could not submit inquiry. Ensure client and developer records exist.");
          });
      });
    }
  };

  const initDeveloperHome = () => {
    if (document.body.dataset.page !== "developer-home") return;
    const leadRows = MOCK.leads
      .map(
        (lead) => `
          <tr>
            <td>${lead.clientName}</td>
            <td>${lead.project}</td>
            <td>${lead.budget}</td>
            <td><span class="pill">${lead.status}</span></td>
          </tr>
        `
      )
      .join("");
    const leadTable = qs("#lead-rows");
    if (leadTable) {
      leadTable.innerHTML = leadRows;
    }
  };

  const initDeveloperProfile = () => {
    if (document.body.dataset.page !== "developer-profile") return;
    const developerId = getActiveDeveloperId();
    apiFetch(`/developers/${developerId}`)
      .then((data) => {
        const dev = toUiDeveloper(data);
        localStorage.setItem("cd-developer-id", dev.id);
        qsa("[data-dev-field]").forEach((input) => {
          const key = input.dataset.devField;
          if (dev[key]) {
            input.value = Array.isArray(dev[key]) ? dev[key].join(", ") : dev[key];
          }
        });
      })
      .catch(() => {
        const dev = MOCK.developers[0];
        qsa("[data-dev-field]").forEach((input) => {
          const key = input.dataset.devField;
          if (dev[key]) {
            input.value = Array.isArray(dev[key]) ? dev[key].join(", ") : dev[key];
          }
        });
      });

    const saveButton = qs("[data-save-profile]");
    if (saveButton) {
      saveButton.addEventListener("click", () => {
        const payload = {};
        qsa("[data-dev-field]").forEach((input) => {
          payload[input.dataset.devField] = input.value;
        });
        apiFetch(`/developers/${developerId}`, {
          method: "PUT",
          body: JSON.stringify({
            name: payload.name || "Developer Name",
            title: payload.title || "",
            bio: payload.bio || "",
            skills: payload.skills || "",
            rate: payload.rate || "",
            location: payload.location || "",
            availability: payload.availability || ""
          })
        })
          .then(() => {
            alert("Profile updated.");
          })
          .catch(() => {
            alert("Could not update profile. Ensure a developer record exists in the database.");
          });
      });
    }
  };

  const initDeveloperPortfolio = () => {
    if (document.body.dataset.page !== "developer-portfolio") return;
    const developerId = getActiveDeveloperId();
    apiFetch(`/developers/${developerId}/portfolio`)
      .then((projects) => {
        const uiProjects = projects.map(toUiPortfolio);
        renderPortfolio("#developer-portfolio-grid", uiProjects.length ? uiProjects : MOCK.portfolios);
      })
      .catch(() => {
        renderPortfolio("#developer-portfolio-grid", MOCK.portfolios);
      });

    const addButton = qs("[data-add-portfolio]");
    if (addButton) {
      addButton.addEventListener("click", () => {
        const title = qs("[data-portfolio-title]")?.value?.trim();
        const description = qs("[data-portfolio-description]")?.value?.trim();
        const techStack = qs("[data-portfolio-stack]")?.value?.trim();
        const imageUrl = qs("[data-portfolio-image]")?.value?.trim();
        if (!title) {
          alert("Please enter a project title.");
          return;
        }
        apiFetch(`/developers/${developerId}/portfolio`, {
          method: "POST",
          body: JSON.stringify({
            title,
            description,
            techStack,
            imageUrl
          })
        })
          .then(() => apiFetch(`/developers/${developerId}/portfolio`))
          .then((projects) => {
            const uiProjects = projects.map(toUiPortfolio);
            renderPortfolio("#developer-portfolio-grid", uiProjects);
          })
          .catch(() => {
            alert("Could not add project. Ensure a developer record exists.");
          });
      });
    }
  };

  const initChat = () => {
    const page = document.body.dataset.page;
    if (page !== "client-messages" && page !== "developer-messages") return;

    const threadsContainer = qs("#chat-threads");
    const messagesContainer = qs("#chat-messages");
    const header = qs("#chat-header");

    if (!threadsContainer || !messagesContainer || !header) return;

    const userId = getActiveUserId();
    let activeThreadId = null;

    const renderThreads = (threads) => {
      threadsContainer.innerHTML = threads
        .map((thread) => {
          const otherParticipant = (thread.participantIds || []).find((id) => id !== userId) || thread.participantIds?.[0];
          const displayName = otherParticipant ? `User #${otherParticipant}` : "Conversation";
          const avatar = { name: displayName, initials: getInitials(displayName), avatarColor: avatarPalette[1] };
          const updatedAt = thread.updatedAt ? new Date(thread.updatedAt).toLocaleDateString() : "Recently";
          return `
            <div class="chat-thread ${thread.id === activeThreadId ? "is-active" : ""}" data-thread="${thread.id}">
              <div class="dev-header">
                ${createAvatar(avatar)}
                <div>
                  <div class="card-title">${displayName}</div>
                  <div class="muted">Conversation</div>
                </div>
              </div>
              <div class="muted">Open chat to view messages</div>
              <div class="dev-meta">
                <span class="pill">${updatedAt}</span>
              </div>
            </div>
          `;
        })
        .join("");
    };

    const renderMessages = (threadId) => {
      apiFetch(`/chat/threads/${threadId}/messages`)
        .then((messages) => {
          const mapped = messages.map((message) => ({
            content: message.content,
            sender: message.senderId === userId ? "me" : "them",
            time: message.createdAt ? new Date(message.createdAt).toLocaleString() : "Now"
          }));
          messagesContainer.innerHTML = mapped
            .map(
              (message) => `
                <div>
                  <div class="chat-bubble ${message.sender === "me" ? "me" : ""}">${message.content}</div>
                  <div class="chat-time">${message.time}</div>
                </div>
              `
            )
            .join("");
          qsa(".chat-thread").forEach((item) => {
            item.classList.toggle("is-active", item.dataset.thread === String(threadId));
          });
        })
        .catch(() => {
          const thread = MOCK.chatThreads.find((item) => item.id === threadId) || MOCK.chatThreads[0];
          header.innerHTML = `
            <div class="dev-header">
              ${createAvatar(thread)}
              <div>
                <div class="card-title">${thread.participantName}</div>
                <div class="muted">${thread.participantRole}</div>
              </div>
            </div>
            <div class="pill">Active now</div>
          `;
          const messages = MOCK.chatMessages[threadId] || [];
          messagesContainer.innerHTML = messages
            .map(
              (message) => `
                <div>
                  <div class="chat-bubble ${message.sender === "me" ? "me" : ""}">${message.content}</div>
                  <div class="chat-time">${message.time}</div>
                </div>
              `
            )
            .join("");
        });
    };

    apiFetch(`/chat/threads?userId=${userId}`)
      .then((threads) => {
        if (!threads.length) {
          throw new Error("no-threads");
        }
        activeThreadId = Number(getQueryParam("thread")) || threads[0].id;
        renderThreads(threads);
        renderMessages(activeThreadId);
        qsa(".chat-thread", threadsContainer).forEach((thread) => {
          thread.addEventListener("click", () => {
            activeThreadId = Number(thread.dataset.thread);
            renderMessages(activeThreadId);
          });
        });
      })
      .catch(() => {
        const initialThread = getQueryParam("thread") || MOCK.chatThreads[0].id;
        activeThreadId = initialThread;
        threadsContainer.innerHTML = "";
        const fallbackThreads = MOCK.chatThreads.map((thread) => ({
          id: thread.id,
          participantIds: [userId, thread.id]
        }));
        renderThreads(fallbackThreads);
        renderMessages(initialThread);
        qsa(".chat-thread", threadsContainer).forEach((thread) => {
          thread.addEventListener("click", () => {
            renderMessages(thread.dataset.thread);
          });
        });
      });

    const sendButton = qs("[data-send-message]");
    const messageInput = qs("[data-message-input]");
    if (sendButton && messageInput) {
      sendButton.addEventListener("click", () => {
        const content = messageInput.value.trim();
        if (!content || !activeThreadId) return;
        apiFetch(`/chat/threads/${activeThreadId}/messages`, {
          method: "POST",
          body: JSON.stringify({ senderId: userId, content })
        })
          .then(() => {
            messageInput.value = "";
            renderMessages(activeThreadId);
          })
          .catch(() => {
            alert("Message could not be sent. Ensure the thread and user exist.");
          });
      });
    }
  };

  const initRoleChip = () => {
    const roleFromQuery = getQueryParam("role");
    const page = document.body.dataset.page || "";
    let inferredRole = null;
    if (page.startsWith("developer")) inferredRole = "developer";
    if (page.startsWith("client")) inferredRole = "client";
    const role = roleFromQuery || inferredRole || getRole();
    setRole(role);
  };

  const init = () => {
    initRoleChip();
    initLanding();
    initLogin();
    initClientHome();
    initClientDevelopers();
    initClientDeveloperProfile();
    initDeveloperHome();
    initDeveloperProfile();
    initDeveloperPortfolio();
    initChat();
  };

  document.addEventListener("DOMContentLoaded", init);

  return {
    routeToHome,
    setRole
  };
})();

