const MOCK = {
  developers: [
    {
      id: "d1",
      name: "Aisha Khan",
      title: "Full-Stack Product Builder",
      skills: ["React", "Spring Boot", "MySQL", "UX"],
      rate: "$55/hr",
      rating: 4.9,
      location: "Karachi, PK",
      availability: "Available in 2 weeks",
      experienceYears: 7,
      bio: "I help founders launch clean, scalable products with thoughtful UX and solid backend foundations.",
      initials: "AK",
      avatarColor: "#0f766e"
    },
    {
      id: "d2",
      name: "Lucas Park",
      title: "MVP Architect",
      skills: ["Next.js", "Node", "PostgreSQL", "Design Systems"],
      rate: "$65/hr",
      rating: 4.8,
      location: "Austin, US",
      availability: "Available now",
      experienceYears: 9,
      bio: "Specialized in fast MVPs, lean product strategy, and beautiful interfaces.",
      initials: "LP",
      avatarColor: "#f59e0b"
    },
    {
      id: "d3",
      name: "Priya Desai",
      title: "Backend + API Specialist",
      skills: ["Spring Boot", "Java", "MySQL", "AWS"],
      rate: "$70/hr",
      rating: 4.95,
      location: "Pune, IN",
      availability: "Available in 1 week",
      experienceYears: 10,
      bio: "Builds resilient services and data layers for fintech, health, and logistics teams.",
      initials: "PD",
      avatarColor: "#1d4ed8"
    },
    {
      id: "d4",
      name: "Jorge Silva",
      title: "Frontend Craftsperson",
      skills: ["Vue", "TypeScript", "Motion", "UI Polish"],
      rate: "$48/hr",
      rating: 4.7,
      location: "Lisbon, PT",
      availability: "Available now",
      experienceYears: 6,
      bio: "Transforms product ideas into delightful, high-performing interfaces.",
      initials: "JS",
      avatarColor: "#db2777"
    },
    {
      id: "d5",
      name: "Maya Chen",
      title: "Product Designer + Dev",
      skills: ["Figma", "React", "Brand Systems", "Research"],
      rate: "$60/hr",
      rating: 4.85,
      location: "Seattle, US",
      availability: "Available in 3 weeks",
      experienceYears: 8,
      bio: "Bridges design and code to ship premium product experiences.",
      initials: "MC",
      avatarColor: "#16a34a"
    },
    {
      id: "d6",
      name: "Noah Kim",
      title: "Growth-Focused Engineer",
      skills: ["Webflow", "React", "Analytics", "SEO"],
      rate: "$45/hr",
      rating: 4.6,
      location: "Seoul, KR",
      availability: "Available now",
      experienceYears: 5,
      bio: "Ships marketing sites and conversion-focused web apps fast.",
      initials: "NK",
      avatarColor: "#ea580c"
    }
  ],
  portfolios: [
    {
      id: "p1",
      developerId: "d1",
      title: "FinSense Budgeting",
      description: "Personal finance app with smart insights and automated reports.",
      techStack: ["React", "Spring Boot", "MySQL"],
      palette: "palette-teal"
    },
    {
      id: "p2",
      developerId: "d1",
      title: "ClinicConnect Portal",
      description: "Patient scheduling and doctor collaboration hub.",
      techStack: ["Vue", "Java", "AWS"],
      palette: "palette-coral"
    },
    {
      id: "p3",
      developerId: "d2",
      title: "ShiftStream Marketplace",
      description: "On-demand staffing platform for hospitality teams.",
      techStack: ["Next.js", "Node", "Stripe"],
      palette: "palette-indigo"
    },
    {
      id: "p4",
      developerId: "d3",
      title: "LogiTrack APIs",
      description: "Scalable API suite for logistics visibility.",
      techStack: ["Spring Boot", "MySQL", "Kafka"],
      palette: "palette-olive"
    },
    {
      id: "p5",
      developerId: "d4",
      title: "Pulse UI Library",
      description: "Design system with motion-first components.",
      techStack: ["Vue", "TypeScript", "Storybook"],
      palette: "palette-indigo"
    },
    {
      id: "p6",
      developerId: "d5",
      title: "Aurora Wellness",
      description: "Brand + product suite for a wellness startup.",
      techStack: ["React", "Figma", "Framer"],
      palette: "palette-coral"
    }
  ],
  reviews: [
    {
      developerId: "d1",
      clientName: "Nimbus Health",
      rating: 5,
      comment: "Aisha brought clarity and speed. We shipped the MVP in half the time."
    },
    {
      developerId: "d1",
      clientName: "Orbit Foods",
      rating: 4.8,
      comment: "Great communication and thoughtful UX decisions."
    },
    {
      developerId: "d2",
      clientName: "Kite Labs",
      rating: 4.7,
      comment: "Lucas built a strong foundation and guided product strategy."
    },
    {
      developerId: "d3",
      clientName: "FreightFox",
      rating: 5,
      comment: "Reliable backend performance and clean API docs."
    }
  ],
  clients: [
    { id: "c1", name: "Nimbus Health", industry: "Healthcare", goal: "Patient portal MVP" },
    { id: "c2", name: "Orbit Foods", industry: "FoodTech", goal: "B2B ordering platform" },
    { id: "c3", name: "Kite Labs", industry: "SaaS", goal: "Analytics dashboard" }
  ],
  leads: [
    { id: "l1", clientName: "Nimbus Health", project: "Patient portal MVP", budget: "$20k-$30k", status: "New" },
    { id: "l2", clientName: "Orbit Foods", project: "B2B ordering platform", budget: "$15k-$25k", status: "In review" },
    { id: "l3", clientName: "Kite Labs", project: "Analytics revamp", budget: "$8k-$12k", status: "Follow-up" }
  ],
  chatThreads: [
    {
      id: "t1",
      participantName: "Nimbus Health",
      participantRole: "Client",
      lastMessage: "Can we scope the patient portal by Friday?",
      updatedAt: "2h ago",
      unread: 2,
      initials: "NH",
      avatarColor: "#0f766e"
    },
    {
      id: "t2",
      participantName: "Lucas Park",
      participantRole: "Developer",
      lastMessage: "Here is the updated timeline proposal.",
      updatedAt: "Yesterday",
      unread: 0,
      initials: "LP",
      avatarColor: "#f59e0b"
    },
    {
      id: "t3",
      participantName: "Maya Chen",
      participantRole: "Developer",
      lastMessage: "Portfolio mockups are ready for review.",
      updatedAt: "Mon",
      unread: 1,
      initials: "MC",
      avatarColor: "#16a34a"
    },
    {
      id: "t4",
      participantName: "Orbit Foods",
      participantRole: "Client",
      lastMessage: "Thanks! We will share our brand assets.",
      updatedAt: "Sun",
      unread: 0,
      initials: "OF",
      avatarColor: "#ea580c"
    }
  ],
  chatMessages: {
    t1: [
      {
        id: "m1",
        sender: "them",
        content: "Hi Aisha, do you have time to kick off this week?",
        time: "Yesterday 4:12 PM"
      },
      {
        id: "m2",
        sender: "me",
        content: "Yes! I can start discovery this Thursday and share a sprint plan.",
        time: "Yesterday 4:35 PM"
      },
      {
        id: "m3",
        sender: "them",
        content: "Great. Can we scope the patient portal by Friday?",
        time: "Today 9:10 AM"
      }
    ],
    t2: [
      {
        id: "m4",
        sender: "them",
        content: "Hey! Sharing a timeline proposal with milestones.",
        time: "Yesterday 2:05 PM"
      },
      {
        id: "m5",
        sender: "me",
        content: "Looks solid. Can we also add a UX review in week 2?",
        time: "Yesterday 2:40 PM"
      }
    ],
    t3: [
      {
        id: "m6",
        sender: "them",
        content: "Portfolio mockups are ready. Want a quick Loom?",
        time: "Mon 11:18 AM"
      },
      {
        id: "m7",
        sender: "me",
        content: "Yes please! Send it over and we will review today.",
        time: "Mon 11:22 AM"
      }
    ],
    t4: [
      {
        id: "m8",
        sender: "them",
        content: "Thanks! We will share our brand assets today.",
        time: "Sun 6:10 PM"
      }
    ]
  }
};
